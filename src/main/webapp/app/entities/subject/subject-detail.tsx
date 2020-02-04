import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subject.reducer';
import { ISubject } from 'app/shared/model/subject.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubjectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubjectDetail = (props: ISubjectDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { subjectEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.subject.detail.title">Subject</Translate> [<b>{subjectEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="topic">
              <Translate contentKey="conferunsApp.subject.topic">Topic</Translate>
            </span>
          </dt>
          <dd>{subjectEntity.topic}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="conferunsApp.subject.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{subjectEntity.difficulty}</dd>
          <dt>
            <span id="audience">
              <Translate contentKey="conferunsApp.subject.audience">Audience</Translate>
            </span>
          </dt>
          <dd>{subjectEntity.audience}</dd>
        </dl>
        <Button tag={Link} to="/subject" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subject/${subjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ subject }: IRootState) => ({
  subjectEntity: subject.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubjectDetail);
