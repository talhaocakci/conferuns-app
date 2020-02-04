import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/presenter.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPresenterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresenterUpdate = (props: IPresenterUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { presenterEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/presenter');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...presenterEntity,
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
          <h2 id="conferunsApp.presenter.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.presenter.home.createOrEditLabel">Create or edit a Presenter</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : presenterEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="presenter-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="presenter-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="presenterIdLabel" for="presenter-presenterId">
                  <Translate contentKey="conferunsApp.presenter.presenterId">Presenter Id</Translate>
                </Label>
                <AvField id="presenter-presenterId" type="string" className="form-control" name="presenterId" />
              </AvGroup>
              <AvGroup>
                <Label id="totalTechnicalPointsLabel" for="presenter-totalTechnicalPoints">
                  <Translate contentKey="conferunsApp.presenter.totalTechnicalPoints">Total Technical Points</Translate>
                </Label>
                <AvField id="presenter-totalTechnicalPoints" type="string" className="form-control" name="totalTechnicalPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageTechnicalPointsLabel" for="presenter-averageTechnicalPoints">
                  <Translate contentKey="conferunsApp.presenter.averageTechnicalPoints">Average Technical Points</Translate>
                </Label>
                <AvField id="presenter-averageTechnicalPoints" type="string" className="form-control" name="averageTechnicalPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="totalSpeakingPointsLabel" for="presenter-totalSpeakingPoints">
                  <Translate contentKey="conferunsApp.presenter.totalSpeakingPoints">Total Speaking Points</Translate>
                </Label>
                <AvField id="presenter-totalSpeakingPoints" type="string" className="form-control" name="totalSpeakingPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageSpeakingPointsLabel" for="presenter-averageSpeakingPoints">
                  <Translate contentKey="conferunsApp.presenter.averageSpeakingPoints">Average Speaking Points</Translate>
                </Label>
                <AvField id="presenter-averageSpeakingPoints" type="string" className="form-control" name="averageSpeakingPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="totalExcitementPointsLabel" for="presenter-totalExcitementPoints">
                  <Translate contentKey="conferunsApp.presenter.totalExcitementPoints">Total Excitement Points</Translate>
                </Label>
                <AvField id="presenter-totalExcitementPoints" type="string" className="form-control" name="totalExcitementPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageExcitementPointsLabel" for="presenter-averageExcitementPoints">
                  <Translate contentKey="conferunsApp.presenter.averageExcitementPoints">Average Excitement Points</Translate>
                </Label>
                <AvField id="presenter-averageExcitementPoints" type="string" className="form-control" name="averageExcitementPoints" />
              </AvGroup>
              <AvGroup>
                <Label for="presenter-user">
                  <Translate contentKey="conferunsApp.presenter.user">User</Translate>
                </Label>
                <AvInput id="presenter-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/presenter" replace color="info">
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
  users: storeState.userManagement.users,
  presenterEntity: storeState.presenter.entity,
  loading: storeState.presenter.loading,
  updating: storeState.presenter.updating,
  updateSuccess: storeState.presenter.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresenterUpdate);
