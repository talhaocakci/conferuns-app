import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/conferuns/talk-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TalkHistoryDetail extends React.Component<ITalkHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { talkHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsTalkHistory.detail.title">TalkHistory</Translate> [<b>{talkHistoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={talkHistoryEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="totalAudience">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.totalAudience">Total Audience</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.totalAudience}</dd>
            <dt>
              <span id="totalTechnicalPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.totalTechnicalPoints">Total Technical Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.totalTechnicalPoints}</dd>
            <dt>
              <span id="averageTechnicalPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.averageTechnicalPoints">Average Technical Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.averageTechnicalPoints}</dd>
            <dt>
              <span id="totalSpeakingPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.totalSpeakingPoints}</dd>
            <dt>
              <span id="averageSpeakingPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.averageSpeakingPoints}</dd>
            <dt>
              <span id="totalExcitementPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.totalExcitementPoints">Total Excitement Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.totalExcitementPoints}</dd>
            <dt>
              <span id="averageExcitementPoints">
                <Translate contentKey="conferunsApp.conferunsTalkHistory.averageExcitementPoints">Average Excitement Points</Translate>
              </span>
            </dt>
            <dd>{talkHistoryEntity.averageExcitementPoints}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsTalkHistory.presenter">Presenter</Translate>
            </dt>
            <dd>{talkHistoryEntity.presenterId ? talkHistoryEntity.presenterId : ''}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsTalkHistory.talk">Talk</Translate>
            </dt>
            <dd>{talkHistoryEntity.talkId ? talkHistoryEntity.talkId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/talk-history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/talk-history/${talkHistoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ talkHistory }: IRootState) => ({
  talkHistoryEntity: talkHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TalkHistoryDetail);
