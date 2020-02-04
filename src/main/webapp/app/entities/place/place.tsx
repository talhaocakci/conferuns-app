import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './place.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlaceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Place = (props: IPlaceProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { placeList, match } = props;
  return (
    <div>
      <h2 id="place-heading">
        <Translate contentKey="conferunsApp.place.home.title">Places</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.place.home.createLabel">Create new Place</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {placeList && placeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.placeId">Place Id</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.state">State</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.city">City</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.district">District</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.street">Street</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.place.doorNo">Door No</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {placeList.map((place, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${place.id}`} color="link" size="sm">
                      {place.id}
                    </Button>
                  </td>
                  <td>{place.placeId}</td>
                  <td>{place.name}</td>
                  <td>{place.country}</td>
                  <td>{place.state}</td>
                  <td>{place.city}</td>
                  <td>{place.district}</td>
                  <td>{place.street}</td>
                  <td>{place.doorNo}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${place.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${place.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${place.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.place.home.notFound">No Places found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ place }: IRootState) => ({
  placeList: place.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Place);
