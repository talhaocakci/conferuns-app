import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/conferuns/presenter.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPresenterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPresenterUpdateState {
  isNew: boolean;
  userId: string;
}

export class PresenterUpdate extends React.Component<IPresenterUpdateProps, IPresenterUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { presenterEntity } = this.props;
      const entity = {
        ...presenterEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/presenter');
  };

  render() {
    const { presenterEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsPresenter.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsPresenter.home.createOrEditLabel">Create or edit a Presenter</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : presenterEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="presenter-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="presenterIdLabel" for="presenterId">
                    <Translate contentKey="conferunsApp.conferunsPresenter.presenterId">Presenter Id</Translate>
                  </Label>
                  <AvField id="presenter-presenterId" type="string" className="form-control" name="presenterId" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalTechnicalPointsLabel" for="totalTechnicalPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.totalTechnicalPoints">Total Technical Points</Translate>
                  </Label>
                  <AvField id="presenter-totalTechnicalPoints" type="string" className="form-control" name="totalTechnicalPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageTechnicalPointsLabel" for="averageTechnicalPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.averageTechnicalPoints">Average Technical Points</Translate>
                  </Label>
                  <AvField id="presenter-averageTechnicalPoints" type="string" className="form-control" name="averageTechnicalPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalSpeakingPointsLabel" for="totalSpeakingPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.totalSpeakingPoints">Total Speaking Points</Translate>
                  </Label>
                  <AvField id="presenter-totalSpeakingPoints" type="string" className="form-control" name="totalSpeakingPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageSpeakingPointsLabel" for="averageSpeakingPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.averageSpeakingPoints">Average Speaking Points</Translate>
                  </Label>
                  <AvField id="presenter-averageSpeakingPoints" type="string" className="form-control" name="averageSpeakingPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalExcitementPointsLabel" for="totalExcitementPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.totalExcitementPoints">Total Excitement Points</Translate>
                  </Label>
                  <AvField id="presenter-totalExcitementPoints" type="string" className="form-control" name="totalExcitementPoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="averageExcitementPointsLabel" for="averageExcitementPoints">
                    <Translate contentKey="conferunsApp.conferunsPresenter.averageExcitementPoints">Average Excitement Points</Translate>
                  </Label>
                  <AvField id="presenter-averageExcitementPoints" type="string" className="form-control" name="averageExcitementPoints" />
                </AvGroup>
                <AvGroup>
                  <Label for="user.id">
                    <Translate contentKey="conferunsApp.conferunsPresenter.user">User</Translate>
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
                <Button tag={Link} id="cancel-save" to="/entity/presenter" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PresenterUpdate);
