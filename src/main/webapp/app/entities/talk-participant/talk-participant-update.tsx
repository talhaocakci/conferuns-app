import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITalk } from 'app/shared/model/talk.model';
import { getEntities as getTalks } from 'app/entities/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk-participant.reducer';
import { ITalkParticipant } from 'app/shared/model/talk-participant.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkParticipantUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkParticipantUpdate = (props: ITalkParticipantUpdateProps) => {
  const [idstalks, setIdstalks] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { talkParticipantEntity, talks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/talk-participant');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTalks();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...talkParticipantEntity,
        ...values,
        talks: mapIdList(values.talks)
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="conferunsApp.talkParticipant.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.talkParticipant.home.createOrEditLabel">Create or edit a TalkParticipant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : talkParticipantEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="talk-participant-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="talk-participant-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="checkedInLabel">
                  <AvInput id="talk-participant-checkedIn" type="checkbox" className="form-check-input" name="checkedIn" />
                  <Translate contentKey="conferunsApp.talkParticipant.checkedIn">Checked In</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="plannedToGoLabel">
                  <AvInput id="talk-participant-plannedToGo" type="checkbox" className="form-check-input" name="plannedToGo" />
                  <Translate contentKey="conferunsApp.talkParticipant.plannedToGo">Planned To Go</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="favoritedLabel">
                  <AvInput id="talk-participant-favorited" type="checkbox" className="form-check-input" name="favorited" />
                  <Translate contentKey="conferunsApp.talkParticipant.favorited">Favorited</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="talk-participant-talks">
                  <Translate contentKey="conferunsApp.talkParticipant.talks">Talks</Translate>
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
              <Button tag={Link} id="cancel-save" to="/talk-participant" replace color="info">
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
};

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

export default connect(mapStateToProps, mapDispatchToProps)(TalkParticipantUpdate);
