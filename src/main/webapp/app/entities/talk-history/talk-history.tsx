import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/talk-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TalkHistory = (props: ITalkHistoryProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { talkHistoryList, match } = props;
  return (
    <div>
      <h2 id="talk-history-heading">
        <Translate contentKey="conferunsApp.talkHistory.home.title">Talk Histories</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.talkHistory.home.createLabel">Create new Talk History</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {talkHistoryList && talkHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.totalAudience">Total Audience</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.totalTechnicalPoints">Total Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.averageTechnicalPoints">Average Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.totalExcitementPoints">Total Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.averageExcitementPoints">Average Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.talk">Talk</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talkHistory.presenter">Presenter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {talkHistoryList.map((talkHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${talkHistory.id}`} color="link" size="sm">
                      {talkHistory.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={talkHistory.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{talkHistory.totalAudience}</td>
                  <td>{talkHistory.totalTechnicalPoints}</td>
                  <td>{talkHistory.averageTechnicalPoints}</td>
                  <td>{talkHistory.totalSpeakingPoints}</td>
                  <td>{talkHistory.averageSpeakingPoints}</td>
                  <td>{talkHistory.totalExcitementPoints}</td>
                  <td>{talkHistory.averageExcitementPoints}</td>
                  <td>{talkHistory.talkId ? <Link to={`talk/${talkHistory.talkId}`}>{talkHistory.talkId}</Link> : ''}</td>
                  <td>
                    {talkHistory.presenterId ? <Link to={`presenter/${talkHistory.presenterId}`}>{talkHistory.presenterId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.talkHistory.home.notFound">No Talk Histories found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ talkHistory }: IRootState) => ({
  talkHistoryList: talkHistory.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TalkHistory);
