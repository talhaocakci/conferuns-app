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
import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/conferuns/talk-history.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITalkHistoryUpdateState {
  isNew: boolean;
  presenterId: string;
  talkId: string;
}

export class TalkHistoryUpdate extends React.Component<ITalkHistoryUpdateProps, ITalkHistoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      presenterId: '0',
      talkId: '0',
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
    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const { talkHistoryEntity } = this.props;
      const entity = {
        ...talkHistoryEntity,
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
    this.props.history.push('/entity/talk-history');
  };

  render() {
    const { talkHistoryEntity, presenters, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsTalkHistory.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsTalkHistory.home.createOrEditLabel">Create or edit a TalkHistory</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : talkHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="talk-history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.date">Date</Translate>
                  </Label>
                  <AvInput
                    id="talk-history-date"
                    type="datetime-local"
                    className="form-control"
                    name="date"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.talkHistoryEntity.date)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="totalAudienceLabel" for="totalAudience">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.totalAudience">Total Audience</Translate>
                  </Label>
                  <AvField id="talk-history-totalAudience" type="string" className="form-control" name="totalAudience" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalTechnicalPointsLabel" for="totalTechnicalPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.totalTechnicalPoints">Total Technical Points</Translate>
                  </Label>
                  <AvField id="talk-history-totalTechnicalPoints" type="string" className="form-control" name="totalTechnicalPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageTechnicalPointsLabel" for="averageTechnicalPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.averageTechnicalPoints">Average Technical Points</Translate>
                  </Label>
                  <AvField id="talk-history-averageTechnicalPoints" type="string" className="form-control" name="averageTechnicalPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalSpeakingPointsLabel" for="totalSpeakingPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
                  </Label>
                  <AvField id="talk-history-totalSpeakingPoints" type="string" className="form-control" name="totalSpeakingPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageSpeakingPointsLabel" for="averageSpeakingPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
                  </Label>
                  <AvField id="talk-history-averageSpeakingPoints" type="string" className="form-control" name="averageSpeakingPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalExcitementPointsLabel" for="totalExcitementPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.totalExcitementPoints">Total Excitement Points</Translate>
                  </Label>
                  <AvField id="talk-history-totalExcitementPoints" type="string" className="form-control" name="totalExcitementPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageExcitementPointsLabel" for="averageExcitementPoints">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.averageExcitementPoints">Average Excitement Points</Translate>
                  </Label>
                  <AvField
                    id="talk-history-averageExcitementPoints"
                    type="string"
                    className="form-control"
                    name="averageExcitementPoints"
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="presenter.id">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.presenter">Presenter</Translate>
                  </Label>
                  <AvInput id="talk-history-presenter" type="select" className="form-control" name="presenterId">
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
                <AvGroup>
                  <Label for="talk.id">
                    <Translate contentKey="conferunsApp.conferunsTalkHistory.talk">Talk</Translate>
                  </Label>
                  <AvInput id="talk-history-talk" type="select" className="form-control" name="talkId">
                    <option value="" key="0" />
                    {talks
                      ? talks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/talk-history" replace color="info">
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
  talks: storeState.talk.entities,
  talkHistoryEntity: storeState.talkHistory.entity,
  loading: storeState.talkHistory.loading,
  updating: storeState.talkHistory.updating,
  updateSuccess: storeState.talkHistory.updateSuccess
});

const mapDispatchToProps = {
  getPresenters,
  getTalks,
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
)(TalkHistoryUpdate);
