import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { getEntity, updateEntity, createEntity, reset } from './room.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRoomUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RoomUpdate = (props: IRoomUpdateProps) => {
  const [placeId, setPlaceId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { roomEntity, places, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/room');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPlaces();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...roomEntity,
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
          <h2 id="conferunsApp.room.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.room.home.createOrEditLabel">Create or edit a Room</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : roomEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="room-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="room-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="roomIdLabel" for="room-roomId">
                  <Translate contentKey="conferunsApp.room.roomId">Room Id</Translate>
                </Label>
                <AvField id="room-roomId" type="string" className="form-control" name="roomId" />
              </AvGroup>
              <AvGroup>
                <Label id="roomNameLabel" for="room-roomName">
                  <Translate contentKey="conferunsApp.room.roomName">Room Name</Translate>
                </Label>
                <AvField id="room-roomName" type="text" name="roomName" />
              </AvGroup>
              <AvGroup>
                <Label id="floorLabel" for="room-floor">
                  <Translate contentKey="conferunsApp.room.floor">Floor</Translate>
                </Label>
                <AvField id="room-floor" type="string" className="form-control" name="floor" />
              </AvGroup>
              <AvGroup>
                <Label id="capacityLabel" for="room-capacity">
                  <Translate contentKey="conferunsApp.room.capacity">Capacity</Translate>
                </Label>
                <AvField id="room-capacity" type="string" className="form-control" name="capacity" />
              </AvGroup>
              <AvGroup>
                <Label for="room-place">
                  <Translate contentKey="conferunsApp.room.place">Place</Translate>
                </Label>
                <AvInput id="room-place" type="select" className="form-control" name="placeId">
                  <option value="" key="0" />
                  {places
                    ? places.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/room" replace color="info">
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
  places: storeState.place.entities,
  roomEntity: storeState.room.entity,
  loading: storeState.room.loading,
  updating: storeState.room.updating,
  updateSuccess: storeState.room.updateSuccess
});

const mapDispatchToProps = {
  getPlaces,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RoomUpdate);
