import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './subject.reducer';
import { ISubject } from 'app/shared/model/subject.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubjectUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubjectUpdate = (props: ISubjectUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { subjectEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/subject');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...subjectEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="conferunsApp.subject.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.subject.home.createOrEditLabel">Create or edit a Subject</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : subjectEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="subject-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="subject-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="topicLabel" for="subject-topic">
                  <Translate contentKey="conferunsApp.subject.topic">Topic</Translate>
                </Label>
                <AvInput
                  id="subject-topic"
                  type="select"
                  className="form-control"
                  name="topic"
                  value={(!isNew && subjectEntity.topic) || 'SOFTWARE_ENGINEERING'}
                >
                  <option value="SOFTWARE_ENGINEERING">{translate('conferunsApp.ConferenceTopic.SOFTWARE_ENGINEERING')}</option>
                  <option value="FINANCE">{translate('conferunsApp.ConferenceTopic.FINANCE')}</option>
                  <option value="SPORTS">{translate('conferunsApp.ConferenceTopic.SPORTS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="difficultyLabel" for="subject-difficulty">
                  <Translate contentKey="conferunsApp.subject.difficulty">Difficulty</Translate>
                </Label>
                <AvField id="subject-difficulty" type="string" className="form-control" name="difficulty" />
              </AvGroup>
              <AvGroup>
                <Label id="audienceLabel" for="subject-audience">
                  <Translate contentKey="conferunsApp.subject.audience">Audience</Translate>
                </Label>
                <AvInput
                  id="subject-audience"
                  type="select"
                  className="form-control"
                  name="audience"
                  value={(!isNew && subjectEntity.audience) || 'BEGINNER'}
                >
                  <option value="BEGINNER">{translate('conferunsApp.Audience.BEGINNER')}</option>
                  <option value="MID_LEVEL">{translate('conferunsApp.Audience.MID_LEVEL')}</option>
                  <option value="EXPERIENCED">{translate('conferunsApp.Audience.EXPERIENCED')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/subject" replace color="info">
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
};

const mapStateToProps = (storeState: IRootState) => ({
  subjectEntity: storeState.subject.entity,
  loading: storeState.subject.loading,
  updating: storeState.subject.updating,
  updateSuccess: storeState.subject.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubjectUpdate);
