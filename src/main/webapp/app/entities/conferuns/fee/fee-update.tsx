import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './fee.reducer';
import { IFee } from 'app/shared/model/conferuns/fee.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFeeUpdateState {
  isNew: boolean;
}

export class FeeUpdate extends React.Component<IFeeUpdateProps, IFeeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.fromTime = convertDateTimeToServer(values.fromTime);
    values.tillTime = convertDateTimeToServer(values.tillTime);

    if (errors.length === 0) {
      const { feeEntity } = this.props;
      const entity = {
        ...feeEntity,
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
    this.props.history.push('/entity/fee');
  };

  render() {
    const { feeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsFee.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsFee.home.createOrEditLabel">Create or edit a Fee</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : feeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="fee-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="conferenceIdLabel" for="conferenceId">
                    <Translate contentKey="conferunsApp.conferunsFee.conferenceId">Conference Id</Translate>
                  </Label>
                  <AvField id="fee-conferenceId" type="string" className="form-control" name="conferenceId" />
                </AvGroup>
                <AvGroup>
                  <Label id="feeLabelLabel" for="feeLabel">
                    <Translate contentKey="conferunsApp.conferunsFee.feeLabel">Fee Label</Translate>
                  </Label>
                  <AvField id="fee-feeLabel" type="text" name="feeLabel" />
                </AvGroup>
                <AvGroup>
                  <Label id="fromTimeLabel" for="fromTime">
                    <Translate contentKey="conferunsApp.conferunsFee.fromTime">From Time</Translate>
                  </Label>
                  <AvInput
                    id="fee-fromTime"
                    type="datetime-local"
                    className="form-control"
                    name="fromTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.feeEntity.fromTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tillTimeLabel" for="tillTime">
                    <Translate contentKey="conferunsApp.conferunsFee.tillTime">Till Time</Translate>
                  </Label>
                  <AvInput
                    id="fee-tillTime"
                    type="datetime-local"
                    className="form-control"
                    name="tillTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.feeEntity.tillTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    <Translate contentKey="conferunsApp.conferunsFee.price">Price</Translate>
                  </Label>
                  <AvField id="fee-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="specialToLabel">
                    <Translate contentKey="conferunsApp.conferunsFee.specialTo">Special To</Translate>
                  </Label>
                  <AvInput
                    id="fee-specialTo"
                    type="select"
                    className="form-control"
                    name="specialTo"
                    value={(!isNew && feeEntity.specialTo) || 'STUDENT'}
                  >
                    <option value="STUDENT">
                      <Translate contentKey="conferunsApp.SpecialParticipantType.STUDENT" />
                    </option>
                    <option value="DISABLE">
                      <Translate contentKey="conferunsApp.SpecialParticipantType.DISABLE" />
                    </option>
                    <option value="WOMAN">
                      <Translate contentKey="conferunsApp.SpecialParticipantType.WOMAN" />
                    </option>
                    <option value="VETERAN">
                      <Translate contentKey="conferunsApp.SpecialParticipantType.VETERAN" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/fee" replace color="info">
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
  }
}

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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeeUpdate);
