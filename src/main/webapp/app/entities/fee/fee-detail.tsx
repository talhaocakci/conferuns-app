import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fee.reducer';
import { IFee } from 'app/shared/model/fee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeeDetail = (props: IFeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { feeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.fee.detail.title">Fee</Translate> [<b>{feeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="conferenceId">
              <Translate contentKey="conferunsApp.fee.conferenceId">Conference Id</Translate>
            </span>
          </dt>
          <dd>{feeEntity.conferenceId}</dd>
          <dt>
            <span id="feeLabel">
              <Translate contentKey="conferunsApp.fee.feeLabel">Fee Label</Translate>
            </span>
          </dt>
          <dd>{feeEntity.feeLabel}</dd>
          <dt>
            <span id="fromTime">
              <Translate contentKey="conferunsApp.fee.fromTime">From Time</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={feeEntity.fromTime} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="tillTime">
              <Translate contentKey="conferunsApp.fee.tillTime">Till Time</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={feeEntity.tillTime} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="price">
              <Translate contentKey="conferunsApp.fee.price">Price</Translate>
            </span>
          </dt>
          <dd>{feeEntity.price}</dd>
          <dt>
            <span id="specialTo">
              <Translate contentKey="conferunsApp.fee.specialTo">Special To</Translate>
            </span>
          </dt>
          <dd>{feeEntity.specialTo}</dd>
        </dl>
        <Button tag={Link} to="/fee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fee/${feeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ fee }: IRootState) => ({
  feeEntity: fee.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeeDetail);
