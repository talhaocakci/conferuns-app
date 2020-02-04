import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/presenter.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresenterProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Presenter = (props: IPresenterProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { presenterList, match } = props;
  return (
    <div>
      <h2 id="presenter-heading">
        <Translate contentKey="conferunsApp.presenter.home.title">Presenters</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.presenter.home.createLabel">Create new Presenter</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {presenterList && presenterList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.presenterId">Presenter Id</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.totalTechnicalPoints">Total Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.averageTechnicalPoints">Average Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.totalSpeakingPoints">Total Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.averageSpeakingPoints">Average Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.totalExcitementPoints">Total Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.averageExcitementPoints">Average Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.presenter.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {presenterList.map((presenter, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${presenter.id}`} color="link" size="sm">
                      {presenter.id}
                    </Button>
                  </td>
                  <td>{presenter.presenterId}</td>
                  <td>{presenter.totalTechnicalPoints}</td>
                  <td>{presenter.averageTechnicalPoints}</td>
                  <td>{presenter.totalSpeakingPoints}</td>
                  <td>{presenter.averageSpeakingPoints}</td>
                  <td>{presenter.totalExcitementPoints}</td>
                  <td>{presenter.averageExcitementPoints}</td>
                  <td>{presenter.userId ? presenter.userId : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${presenter.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${presenter.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${presenter.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.presenter.home.notFound">No Presenters found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ presenter }: IRootState) => ({
  presenterList: presenter.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Presenter);
