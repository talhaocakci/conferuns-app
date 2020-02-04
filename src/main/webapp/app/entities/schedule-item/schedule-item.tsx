import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/schedule-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScheduleItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ScheduleItem = (props: IScheduleItemProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { scheduleItemList, match } = props;
  return (
    <div>
      <h2 id="schedule-item-heading">
        <Translate contentKey="conferunsApp.scheduleItem.home.title">Schedule Items</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.scheduleItem.home.createLabel">Create new Schedule Item</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {scheduleItemList && scheduleItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.scheduleItem.fromTime">From Time</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.scheduleItem.tillTime">Till Time</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.scheduleItem.talk">Talk</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.scheduleItem.conference">Conference</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {scheduleItemList.map((scheduleItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${scheduleItem.id}`} color="link" size="sm">
                      {scheduleItem.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={scheduleItem.fromTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={scheduleItem.tillTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{scheduleItem.talkId ? <Link to={`talk/${scheduleItem.talkId}`}>{scheduleItem.talkId}</Link> : ''}</td>
                  <td>
                    {scheduleItem.conferenceId ? (
                      <Link to={`conference/${scheduleItem.conferenceId}`}>{scheduleItem.conferenceId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.scheduleItem.home.notFound">No Schedule Items found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ scheduleItem }: IRootState) => ({
  scheduleItemList: scheduleItem.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScheduleItem);
