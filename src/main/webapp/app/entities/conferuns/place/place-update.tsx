import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConference } from 'app/shared/model/conferuns/conference.model';
import { getEntities as getConferences } from 'app/entities/conferuns/conference/conference.reducer';
import { getEntity, updateEntity, createEntity, reset } from './place.reducer';
import { IPlace } from 'app/shared/model/conferuns/place.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlaceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPlaceUpdateState {
  isNew: boolean;
  conferencesId: string;
}

export class PlaceUpdate extends React.Component<IPlaceUpdateProps, IPlaceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      conferencesId: '0',
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

    this.props.getConferences();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { placeEntity } = this.props;
      const entity = {
        ...placeEntity,
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
    this.props.history.push('/entity/place');
  };

  render() {
    const { placeEntity, conferences, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsPlace.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsPlace.home.createOrEditLabel">Create or edit a Place</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : placeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="place-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="placeIdLabel" for="placeId">
                    <Translate contentKey="conferunsApp.conferunsPlace.placeId">Place Id</Translate>
                  </Label>
                  <AvField id="place-placeId" type="string" className="form-control" name="placeId" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="conferunsApp.conferunsPlace.name">Name</Translate>
                  </Label>
                  <AvField id="place-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="country">
                    <Translate contentKey="conferunsApp.conferunsPlace.country">Country</Translate>
                  </Label>
                  <AvField id="place-country" type="text" name="country" />
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel" for="state">
                    <Translate contentKey="conferunsApp.conferunsPlace.state">State</Translate>
                  </Label>
                  <AvField id="place-state" type="text" name="state" />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="city">
                    <Translate contentKey="conferunsApp.conferunsPlace.city">City</Translate>
                  </Label>
                  <AvField id="place-city" type="text" name="city" />
                </AvGroup>
                <AvGroup>
                  <Label id="districtLabel" for="district">
                    <Translate contentKey="conferunsApp.conferunsPlace.district">District</Translate>
                  </Label>
                  <AvField id="place-district" type="text" name="district" />
                </AvGroup>
                <AvGroup>
                  <Label id="streetLabel" for="street">
                    <Translate contentKey="conferunsApp.conferunsPlace.street">Street</Translate>
                  </Label>
                  <AvField id="place-street" type="text" name="street" />
                </AvGroup>
                <AvGroup>
                  <Label id="doorNoLabel" for="doorNo">
                    <Translate contentKey="conferunsApp.conferunsPlace.doorNo">Door No</Translate>
                  </Label>
                  <AvField id="place-doorNo" type="text" name="doorNo" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/place" replace color="info">
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
  conferences: storeState.conference.entities,
  placeEntity: storeState.place.entity,
  loading: storeState.place.loading,
  updating: storeState.place.updating,
  updateSuccess: storeState.place.updateSuccess
});

const mapDispatchToProps = {
  getConferences,
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
)(PlaceUpdate);
