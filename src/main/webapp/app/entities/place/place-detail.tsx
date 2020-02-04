import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './place.reducer';
import { IPlace } from 'app/shared/model/place.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlaceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PlaceDetail = (props: IPlaceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { placeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.place.detail.title">Place</Translate> [<b>{placeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="placeId">
              <Translate contentKey="conferunsApp.place.placeId">Place Id</Translate>
            </span>
          </dt>
          <dd>{placeEntity.placeId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="conferunsApp.place.name">Name</Translate>
            </span>
          </dt>
          <dd>{placeEntity.name}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="conferunsApp.place.country">Country</Translate>
            </span>
          </dt>
          <dd>{placeEntity.country}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="conferunsApp.place.state">State</Translate>
            </span>
          </dt>
          <dd>{placeEntity.state}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="conferunsApp.place.city">City</Translate>
            </span>
          </dt>
          <dd>{placeEntity.city}</dd>
          <dt>
            <span id="district">
              <Translate contentKey="conferunsApp.place.district">District</Translate>
            </span>
          </dt>
          <dd>{placeEntity.district}</dd>
          <dt>
            <span id="street">
              <Translate contentKey="conferunsApp.place.street">Street</Translate>
            </span>
          </dt>
          <dd>{placeEntity.street}</dd>
          <dt>
            <span id="doorNo">
              <Translate contentKey="conferunsApp.place.doorNo">Door No</Translate>
            </span>
          </dt>
          <dd>{placeEntity.doorNo}</dd>
        </dl>
        <Button tag={Link} to="/place" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/place/${placeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ place }: IRootState) => ({
  placeEntity: place.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PlaceDetail);
