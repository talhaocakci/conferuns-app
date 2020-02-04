import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './room.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRoomProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Room = (props: IRoomProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { roomList, match } = props;
  return (
    <div>
      <h2 id="room-heading">
        <Translate contentKey="conferunsApp.room.home.title">Rooms</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.room.home.createLabel">Create new Room</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {roomList && roomList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.room.roomId">Room Id</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.room.roomName">Room Name</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.room.floor">Floor</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.room.capacity">Capacity</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.room.place">Place</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roomList.map((room, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${room.id}`} color="link" size="sm">
                      {room.id}
                    </Button>
                  </td>
                  <td>{room.roomId}</td>
                  <td>{room.roomName}</td>
                  <td>{room.floor}</td>
                  <td>{room.capacity}</td>
                  <td>{room.placeId ? <Link to={`place/${room.placeId}`}>{room.placeId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${room.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${room.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${room.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="conferunsApp.room.home.notFound">No Rooms found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ room }: IRootState) => ({
  roomList: room.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Room);
