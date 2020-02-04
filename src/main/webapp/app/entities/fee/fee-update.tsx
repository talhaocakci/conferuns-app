import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './fee.reducer';
import { IFee } from 'app/shared/model/fee.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeeUpdate = (props: IFeeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { feeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/fee');
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
    values.fromTime = convertDateTimeToServer(values.fromTime);
    values.tillTime = convertDateTimeToServer(values.tillTime);

    if (errors.length === 0) {
      const entity = {
        ...feeEntity,
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
          <h2 id="conferunsApp.fee.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.fee.home.createOrEditLabel">Create or edit a Fee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : feeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="fee-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="fee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="conferenceIdLabel" for="fee-conferenceId">
                  <Translate contentKey="conferunsApp.fee.conferenceId">Conference Id</Translate>
                </Label>
                <AvField id="fee-conferenceId" type="string" className="form-control" name="conferenceId" />
              </AvGroup>
              <AvGroup>
                <Label id="feeLabelLabel" for="fee-feeLabel">
                  <Translate contentKey="conferunsApp.fee.feeLabel">Fee Label</Translate>
                </Label>
                <AvField id="fee-feeLabel" type="text" name="feeLabel" />
              </AvGroup>
              <AvGroup>
                <Label id="fromTimeLabel" for="fee-fromTime">
                  <Translate contentKey="conferunsApp.fee.fromTime">From Time</Translate>
                </Label>
                <AvInput
                  id="fee-fromTime"
                  type="datetime-local"
                  className="form-control"
                  name="fromTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.feeEntity.fromTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tillTimeLabel" for="fee-tillTime">
                  <Translate contentKey="conferunsApp.fee.tillTime">Till Time</Translate>
                </Label>
                <AvInput
                  id="fee-tillTime"
                  type="datetime-local"
                  className="form-control"
                  name="tillTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.feeEntity.tillTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="fee-price">
                  <Translate contentKey="conferunsApp.fee.price">Price</Translate>
                </Label>
                <AvField id="fee-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label id="specialToLabel" for="fee-specialTo">
                  <Translate contentKey="conferunsApp.fee.specialTo">Special To</Translate>
                </Label>
                <AvInput
                  id="fee-specialTo"
                  type="select"
                  className="form-control"
                  name="specialTo"
                  value={(!isNew && feeEntity.specialTo) || 'STUDENT'}
                >
                  <option value="STUDENT">{translate('conferunsApp.SpecialParticipantType.STUDENT')}</option>
                  <option value="DISABLE">{translate('conferunsApp.SpecialParticipantType.DISABLE')}</option>
                  <option value="WOMAN">{translate('conferunsApp.SpecialParticipantType.WOMAN')}</option>
                  <option value="VETERAN">{translate('conferunsApp.SpecialParticipantType.VETERAN')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/fee" replace color="info">
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
  feeEntity: storeState.fee.entity,
  loading: storeState.fee.loading,
  updating: storeState.fee.updating,
  updateSuccess: storeState.fee.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeeUpdate);
