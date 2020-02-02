import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/conferuns/presenter.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresenterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PresenterDetail extends React.Component<IPresenterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { presenterEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsPresenter.detail.title">Presenter</Translate> [<b>{presenterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="presenterId">
                <Translate contentKey="conferunsApp.conferunsPresenter.presenterId">Presenter Id</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.presenterId}</dd>
            <dt>
              <span id="totalTechnicalPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.totalTechnicalPoints">Total Technical Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.totalTechnicalPoints}</dd>
            <dt>
              <span id="averageTechnicalPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.averageTechnicalPoints">Average Technical Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.averageTechnicalPoints}</dd>
            <dt>
              <span id="totalSpeakingPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.totalSpeakingPoints">Total Speaking Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.totalSpeakingPoints}</dd>
            <dt>
              <span id="averageSpeakingPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.averageSpeakingPoints">Average Speaking Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.averageSpeakingPoints}</dd>
            <dt>
              <span id="totalExcitementPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.totalExcitementPoints">Total Excitement Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.totalExcitementPoints}</dd>
            <dt>
              <span id="averageExcitementPoints">
                <Translate contentKey="conferunsApp.conferunsPresenter.averageExcitementPoints">Average Excitement Points</Translate>
              </span>
            </dt>
            <dd>{presenterEntity.averageExcitementPoints}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsPresenter.user">User</Translate>
            </dt>
            <dd>{presenterEntity.userId ? presenterEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/presenter" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/presenter/${presenterEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ presenter }: IRootState) => ({
  presenterEntity: presenter.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PresenterDetail);
