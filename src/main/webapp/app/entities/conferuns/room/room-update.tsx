import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlace } from 'app/shared/model/conferuns/place.model';
import { getEntities as getPlaces } from 'app/entities/conferuns/place/place.reducer';
import { getEntity, updateEntity, createEntity, reset } from './room.reducer';
import { IRoom } from 'app/shared/model/conferuns/room.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRoomUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRoomUpdateState {
  isNew: boolean;
  placeId: string;
}

export class RoomUpdate extends React.Component<IRoomUpdateProps, IRoomUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      placeId: '0',
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

    this.props.getPlaces();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { roomEntity } = this.props;
      const entity = {
        ...roomEntity,
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
    this.props.history.push('/entity/room');
  };

  render() {
    const { roomEntity, places, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsRoom.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsRoom.home.createOrEditLabel">Create or edit a Room</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : roomEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="room-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="roomIdLabel" for="roomId">
                    <Translate contentKey="conferunsApp.conferunsRoom.roomId">Room Id</Translate>
                  </Label>
                  <AvField id="room-roomId" type="string" className="form-control" name="roomId" />
                </AvGroup>
                <AvGroup>
                  <Label id="roomNameLabel" for="roomName">
                    <Translate contentKey="conferunsApp.conferunsRoom.roomName">Room Name</Translate>
                  </Label>
                  <AvField id="room-roomName" type="text" name="roomName" />
                </AvGroup>
                <AvGroup>
                  <Label id="floorLabel" for="floor">
                    <Translate contentKey="conferunsApp.conferunsRoom.floor">Floor</Translate>
                  </Label>
                  <AvField id="room-floor" type="string" className="form-control" name="floor" />
                </AvGroup>
                <AvGroup>
                  <Label id="capacityLabel" for="capacity">
                    <Translate contentKey="conferunsApp.conferunsRoom.capacity">Capacity</Translate>
                  </Label>
                  <AvField id="room-capacity" type="string" className="form-control" name="capacity" />
                </AvGroup>
                <AvGroup>
                  <Label for="place.id">
                    <Translate contentKey="conferunsApp.conferunsRoom.place">Place</Translate>
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
                <Button tag={Link} id="cancel-save" to="/entity/room" replace color="info">
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RoomUpdate);
