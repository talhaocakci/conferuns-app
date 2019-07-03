import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fee.reducer';
import { IFee } from 'app/shared/model/conferuns/fee.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FeeDetail extends React.Component<IFeeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { feeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsFee.detail.title">Fee</Translate> [<b>{feeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="conferenceId">
                <Translate contentKey="conferunsApp.conferunsFee.conferenceId">Conference Id</Translate>
              </span>
            </dt>
            <dd>{feeEntity.conferenceId}</dd>
            <dt>
              <span id="feeLabel">
                <Translate contentKey="conferunsApp.conferunsFee.feeLabel">Fee Label</Translate>
              </span>
            </dt>
            <dd>{feeEntity.feeLabel}</dd>
            <dt>
              <span id="fromTime">
                <Translate contentKey="conferunsApp.conferunsFee.fromTime">From Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={feeEntity.fromTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tillTime">
                <Translate contentKey="conferunsApp.conferunsFee.tillTime">Till Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={feeEntity.tillTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="price">
                <Translate contentKey="conferunsApp.conferunsFee.price">Price</Translate>
              </span>
            </dt>
            <dd>{feeEntity.price}</dd>
            <dt>
              <span id="specialTo">
                <Translate contentKey="conferunsApp.conferunsFee.specialTo">Special To</Translate>
              </span>
            </dt>
            <dd>{feeEntity.specialTo}</dd>
          </dl>
          <Button tag={Link} to="/entity/fee" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/fee/${feeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ fee }: IRootState) => ({
  feeEntity: fee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FeeDetail);
