import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConference } from 'app/shared/model/conference.model';
import { getEntities as getConferences } from 'app/entities/conference/conference.reducer';
import { getEntity, updateEntity, createEntity, reset } from './place.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlaceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PlaceUpdate = (props: IPlaceUpdateProps) => {
  const [conferencesId, setConferencesId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { placeEntity, conferences, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/place');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getConferences();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...placeEntity,
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
          <h2 id="conferunsApp.place.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.place.home.createOrEditLabel">Create or edit a Place</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : placeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="place-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="place-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="placeIdLabel" for="place-placeId">
                  <Translate contentKey="conferunsApp.place.placeId">Place Id</Translate>
                </Label>
                <AvField id="place-placeId" type="string" className="form-control" name="placeId" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="place-name">
                  <Translate contentKey="conferunsApp.place.name">Name</Translate>
                </Label>
                <AvField id="place-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="countryLabel" for="place-country">
                  <Translate contentKey="conferunsApp.place.country">Country</Translate>
                </Label>
                <AvField id="place-country" type="text" name="country" />
              </AvGroup>
              <AvGroup>
                <Label id="stateLabel" for="place-state">
                  <Translate contentKey="conferunsApp.place.state">State</Translate>
                </Label>
                <AvField id="place-state" type="text" name="state" />
              </AvGroup>
              <AvGroup>
                <Label id="cityLabel" for="place-city">
                  <Translate contentKey="conferunsApp.place.city">City</Translate>
                </Label>
                <AvField id="place-city" type="text" name="city" />
              </AvGroup>
              <AvGroup>
                <Label id="districtLabel" for="place-district">
                  <Translate contentKey="conferunsApp.place.district">District</Translate>
                </Label>
                <AvField id="place-district" type="text" name="district" />
              </AvGroup>
              <AvGroup>
                <Label id="streetLabel" for="place-street">
                  <Translate contentKey="conferunsApp.place.street">Street</Translate>
                </Label>
                <AvField id="place-street" type="text" name="street" />
              </AvGroup>
              <AvGroup>
                <Label id="doorNoLabel" for="place-doorNo">
                  <Translate contentKey="conferunsApp.place.doorNo">Door No</Translate>
                </Label>
                <AvField id="place-doorNo" type="text" name="doorNo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/place" replace color="info">
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

export default connect(mapStateToProps, mapDispatchToProps)(PlaceUpdate);
