import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './room.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoomDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RoomDetail = (props: IRoomDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { roomEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.room.detail.title">Room</Translate> [<b>{roomEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="roomId">
              <Translate contentKey="conferunsApp.room.roomId">Room Id</Translate>
            </span>
          </dt>
          <dd>{roomEntity.roomId}</dd>
          <dt>
            <span id="roomName">
              <Translate contentKey="conferunsApp.room.roomName">Room Name</Translate>
            </span>
          </dt>
          <dd>{roomEntity.roomName}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="conferunsApp.room.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{roomEntity.floor}</dd>
          <dt>
            <span id="capacity">
              <Translate contentKey="conferunsApp.room.capacity">Capacity</Translate>
            </span>
          </dt>
          <dd>{roomEntity.capacity}</dd>
          <dt>
            <Translate contentKey="conferunsApp.room.place">Place</Translate>
          </dt>
          <dd>{roomEntity.placeId ? roomEntity.placeId : ''}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ room }: IRootState) => ({
  roomEntity: room.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RoomDetail);
