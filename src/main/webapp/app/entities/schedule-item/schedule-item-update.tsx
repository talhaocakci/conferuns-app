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
import { IConference } from 'app/shared/model/conference.model';
import { getEntities as getConferences } from 'app/entities/conference/conference.reducer';
import { getEntity, updateEntity, createEntity, reset } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/schedule-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScheduleItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScheduleItemUpdate = (props: IScheduleItemUpdateProps) => {
  const [talkId, setTalkId] = useState('0');
  const [conferenceId, setConferenceId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { scheduleItemEntity, talks, conferences, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/schedule-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTalks();
    props.getConferences();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.fromTime = convertDateTimeToServer(values.fromTime);
    values.tillTime = convertDateTimeToServer(values.tillTime);

    if (errors.length === 0) {
      const entity = {
        ...scheduleItemEntity,
        ...values
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
          <h2 id="conferunsApp.scheduleItem.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.scheduleItem.home.createOrEditLabel">Create or edit a ScheduleItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : scheduleItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="schedule-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="schedule-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fromTimeLabel" for="schedule-item-fromTime">
                  <Translate contentKey="conferunsApp.scheduleItem.fromTime">From Time</Translate>
                </Label>
                <AvInput
                  id="schedule-item-fromTime"
                  type="datetime-local"
                  className="form-control"
                  name="fromTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.scheduleItemEntity.fromTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tillTimeLabel" for="schedule-item-tillTime">
                  <Translate contentKey="conferunsApp.scheduleItem.tillTime">Till Time</Translate>
                </Label>
                <AvInput
                  id="schedule-item-tillTime"
                  type="datetime-local"
                  className="form-control"
                  name="tillTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.scheduleItemEntity.tillTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="schedule-item-talk">
                  <Translate contentKey="conferunsApp.scheduleItem.talk">Talk</Translate>
                </Label>
                <AvInput id="schedule-item-talk" type="select" className="form-control" name="talkId">
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
              <AvGroup>
                <Label for="schedule-item-conference">
                  <Translate contentKey="conferunsApp.scheduleItem.conference">Conference</Translate>
                </Label>
                <AvInput id="schedule-item-conference" type="select" className="form-control" name="conferenceId">
                  <option value="" key="0" />
                  {conferences
                    ? conferences.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/schedule-item" replace color="info">
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
  conferences: storeState.conference.entities,
  scheduleItemEntity: storeState.scheduleItem.entity,
  loading: storeState.scheduleItem.loading,
  updating: storeState.scheduleItem.updating,
  updateSuccess: storeState.scheduleItem.updateSuccess
});

const mapDispatchToProps = {
  getTalks,
  getConferences,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScheduleItemUpdate);
