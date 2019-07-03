import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPresenter } from 'app/shared/model/conferuns/presenter.model';
import { getEntities as getPresenters } from 'app/entities/conferuns/presenter/presenter.reducer';
import { ITalkParticipant } from 'app/shared/model/conferuns/talk-participant.model';
import { getEntities as getTalkParticipants } from 'app/entities/conferuns/talk-participant/talk-participant.reducer';
import { IConference } from 'app/shared/model/conferuns/conference.model';
import { getEntities as getConferences } from 'app/entities/conferuns/conference/conference.reducer';
import { ITalkTag } from 'app/shared/model/conferuns/talk-tag.model';
import { getEntities as getTalkTags } from 'app/entities/conferuns/talk-tag/talk-tag.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk.reducer';
import { ITalk } from 'app/shared/model/conferuns/talk.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITalkUpdateState {
  isNew: boolean;
  presenterId: string;
  participantsId: string;
  conferencesId: string;
  tagsId: string;
}

export class TalkUpdate extends React.Component<ITalkUpdateProps, ITalkUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      presenterId: '0',
      participantsId: '0',
      conferencesId: '0',
      tagsId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPresenters();
    this.props.getTalkParticipants();
    this.props.getConferences();
    this.props.getTalkTags();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { talkEntity } = this.props;
      const entity = {
        ...talkEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/talk');
  };

  render() {
    const { talkEntity, presenters, talkParticipants, conferences, talkTags, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsTalk.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsTalk.home.createOrEditLabel">Create or edit a Talk</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : talkEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="talk-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="languageLabel">
                    <Translate contentKey="conferunsApp.conferunsTalk.language">Language</Translate>
                  </Label>
                  <AvInput
                    id="talk-language"
                    type="select"
                    className="form-control"
                    name="language"
                    value={(!isNew && talkEntity.language) || 'ENGLISH'}
                  >
                    <option value="ENGLISH">
                      <Translate contentKey="conferunsApp.Language.ENGLISH" />
                    </option>
                    <option value="TURKISH">
                      <Translate contentKey="conferunsApp.Language.TURKISH" />
                    </option>
                    <option value="FRENCH">
                      <Translate contentKey="conferunsApp.Language.FRENCH" />
                    </option>
                    <option value="SPANISH">
                      <Translate contentKey="conferunsApp.Language.SPANISH" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="mainTopicLabel" for="mainTopic">
                    <Translate contentKey="conferunsApp.conferunsTalk.mainTopic">Main Topic</Translate>
                  </Label>
                  <AvField id="talk-mainTopic" type="text" name="mainTopic" />
                </AvGroup>
                <AvGroup>
                  <Label id="subTopicLabel" for="subTopic">
                    <Translate contentKey="conferunsApp.conferunsTalk.subTopic">Sub Topic</Translate>
                  </Label>
                  <AvField id="talk-subTopic" type="text" name="subTopic" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="conferunsApp.conferunsTalk.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="talk-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && talkEntity.status) || 'DRAFT'}
                  >
                    <option value="DRAFT">
                      <Translate contentKey="conferunsApp.TalkStatus.DRAFT" />
                    </option>
                    <option value="IN_REVIEW">
                      <Translate contentKey="conferunsApp.TalkStatus.IN_REVIEW" />
                    </option>
                    <option value="NEED_MORE_REVIEW">
                      <Translate contentKey="conferunsApp.TalkStatus.NEED_MORE_REVIEW" />
                    </option>
                    <option value="REJECTED">
                      <Translate contentKey="conferunsApp.TalkStatus.REJECTED" />
                    </option>
                    <option value="APPROVED">
                      <Translate contentKey="conferunsApp.TalkStatus.APPROVED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="presenter.id">
                    <Translate contentKey="conferunsApp.conferunsTalk.presenter">Presenter</Translate>
                  </Label>
                  <AvInput id="talk-presenter" type="select" className="form-control" name="presenterId">
                    <option value="" key="0" />
                    {presenters
                      ? presenters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/talk" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  presenters: storeState.presenter.entities,
  talkParticipants: storeState.talkParticipant.entities,
  conferences: storeState.conference.entities,
  talkTags: storeState.talkTag.entities,
  talkEntity: storeState.talk.entity,
  loading: storeState.talk.loading,
  updating: storeState.talk.updating,
  updateSuccess: storeState.talk.updateSuccess
});

const mapDispatchToProps = {
  getPresenters,
  getTalkParticipants,
  getConferences,
  getTalkTags,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TalkUpdate);
