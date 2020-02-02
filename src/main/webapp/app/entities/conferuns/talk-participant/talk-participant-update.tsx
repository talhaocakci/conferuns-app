import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk-participant.reducer';
import { ITalkParticipant } from 'app/shared/model/conferuns/talk-participant.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkParticipantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITalkParticipantUpdateState {
  isNew: boolean;
  idstalks: any[];
}

export class TalkParticipantUpdate extends React.Component<ITalkParticipantUpdateProps, ITalkParticipantUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idstalks: [],
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

    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { talkParticipantEntity } = this.props;
      const entity = {
        ...talkParticipantEntity,
        ...values,
        talks: mapIdList(values.talks)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/talk-participant');
  };

  render() {
    const { talkParticipantEntity, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsTalkParticipant.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsTalkParticipant.home.createOrEditLabel">
                Create or edit a TalkParticipant
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : talkParticipantEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="talk-participant-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="checkedInLabel" check>
                    <AvInput id="talk-participant-checkedIn" type="checkbox" className="form-control" name="checkedIn" />
                    <Translate contentKey="conferunsApp.conferunsTalkParticipant.checkedIn">Checked In</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="plannedToGoLabel" check>
                    <AvInput id="talk-participant-plannedToGo" type="checkbox" className="form-control" name="plannedToGo" />
                    <Translate contentKey="conferunsApp.conferunsTalkParticipant.plannedToGo">Planned To Go</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="favoritedLabel" check>
                    <AvInput id="talk-participant-favorited" type="checkbox" className="form-control" name="favorited" />
                    <Translate contentKey="conferunsApp.conferunsTalkParticipant.favorited">Favorited</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="talks">
                    <Translate contentKey="conferunsApp.conferunsTalkParticipant.talks">Talks</Translate>
                  </Label>
                  <AvInput
                    id="talk-participant-talks"
                    type="select"
                    multiple
                    className="form-control"
                    name="talks"
                    value={talkParticipantEntity.talks && talkParticipantEntity.talks.map(e => e.id)}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/talk-participant" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
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
  talks: storeState.talk.entities,
  talkParticipantEntity: storeState.talkParticipant.entity,
  loading: storeState.talkParticipant.loading,
  updating: storeState.talkParticipant.updating,
  updateSuccess: storeState.talkParticipant.updateSuccess
});

const mapDispatchToProps = {
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
)(TalkParticipantUpdate);
