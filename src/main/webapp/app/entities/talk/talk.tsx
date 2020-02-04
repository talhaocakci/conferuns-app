import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './talk.reducer';
import { ITalk } from 'app/shared/model/talk.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Talk = (props: ITalkProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { talkList, match } = props;
  return (
    <div>
      <h2 id="talk-heading">
        <Translate contentKey="conferunsApp.talk.home.title">Talks</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.talk.home.createLabel">Create new Talk</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {talkList && talkList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talk.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talk.mainTopic">Main Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talk.subTopic">Sub Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talk.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.talk.presenter">Presenter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {talkList.map((talk, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${talk.id}`} color="link" size="sm">
                      {talk.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`conferunsApp.Language.${talk.language}`} />
                  </td>
                  <td>{talk.mainTopic}</td>
                  <td>{talk.subTopic}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.TalkStatus.${talk.status}`} />
                  </td>
                  <td>{talk.presenterId ? <Link to={`presenter/${talk.presenterId}`}>{talk.presenterId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${talk.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talk.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talk.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.talk.home.notFound">No Talks found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ talk }: IRootState) => ({
  talkList: talk.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Talk);
