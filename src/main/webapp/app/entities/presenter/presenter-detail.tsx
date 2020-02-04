import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/presenter.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresenterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PresenterDetail = (props: IPresenterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { presenterEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.presenter.detail.title">Presenter</Translate> [<b>{presenterEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="presenterId">
              <Translate contentKey="conferunsApp.presenter.presenterId">Presenter Id</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.presenterId}</dd>
          <dt>
            <span id="totalTechnicalPoints">
              <Translate contentKey="conferunsApp.presenter.totalTechnicalPoints">Total Technical Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.totalTechnicalPoints}</dd>
          <dt>
            <span id="averageTechnicalPoints">
              <Translate contentKey="conferunsApp.presenter.averageTechnicalPoints">Average Technical Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.averageTechnicalPoints}</dd>
          <dt>
            <span id="totalSpeakingPoints">
              <Translate contentKey="conferunsApp.presenter.totalSpeakingPoints">Total Speaking Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.totalSpeakingPoints}</dd>
          <dt>
            <span id="averageSpeakingPoints">
              <Translate contentKey="conferunsApp.presenter.averageSpeakingPoints">Average Speaking Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.averageSpeakingPoints}</dd>
          <dt>
            <span id="totalExcitementPoints">
              <Translate contentKey="conferunsApp.presenter.totalExcitementPoints">Total Excitement Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.totalExcitementPoints}</dd>
          <dt>
            <span id="averageExcitementPoints">
              <Translate contentKey="conferunsApp.presenter.averageExcitementPoints">Average Excitement Points</Translate>
            </span>
          </dt>
          <dd>{presenterEntity.averageExcitementPoints}</dd>
          <dt>
            <Translate contentKey="conferunsApp.presenter.user">User</Translate>
          </dt>
          <dd>{presenterEntity.userId ? presenterEntity.userId : ''}</dd>
        </dl>
        <Button tag={Link} to="/presenter" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/presenter/${presenterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ presenter }: IRootState) => ({
  presenterEntity: presenter.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PresenterDetail);
