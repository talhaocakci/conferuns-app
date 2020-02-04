import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './subject.reducer';
import { ISubject } from 'app/shared/model/subject.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubjectProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Subject = (props: ISubjectProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { subjectList, match } = props;
  return (
    <div>
      <h2 id="subject-heading">
        <Translate contentKey="conferunsApp.subject.home.title">Subjects</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.subject.home.createLabel">Create new Subject</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {subjectList && subjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.subject.topic">Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.subject.difficulty">Difficulty</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.subject.audience">Audience</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subjectList.map((subject, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${subject.id}`} color="link" size="sm">
                      {subject.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`conferunsApp.ConferenceTopic.${subject.topic}`} />
                  </td>
                  <td>{subject.difficulty}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.Audience.${subject.audience}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${subject.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subject.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${subject.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.subject.home.notFound">No Subjects found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ subject }: IRootState) => ({
  subjectList: subject.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Subject);
