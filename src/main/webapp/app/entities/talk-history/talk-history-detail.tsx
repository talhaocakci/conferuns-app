import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/talk-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkHistoryDetail = (props: ITalkHistoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { talkHistoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.talkHistory.detail.title">TalkHistory</Translate> [<b>{talkHistoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="conferunsApp.talkHistory.date">Date</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={talkHistoryEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="totalAudience">
              <Translate contentKey="conferunsApp.talkHistory.totalAudience">Total Audience</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.totalAudience}</dd>
          <dt>
            <span id="totalTechnicalPoints">
              <Translate contentKey="conferunsApp.talkHistory.totalTechnicalPoints">Total Technical Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.totalTechnicalPoints}</dd>
          <dt>
            <span id="averageTechnicalPoints">
              <Translate contentKey="conferunsApp.talkHistory.averageTechnicalPoints">Average Technical Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.averageTechnicalPoints}</dd>
          <dt>
            <span id="totalSpeakingPoints">
              <Translate contentKey="conferunsApp.talkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.totalSpeakingPoints}</dd>
          <dt>
            <span id="averageSpeakingPoints">
              <Translate contentKey="conferunsApp.talkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.averageSpeakingPoints}</dd>
          <dt>
            <span id="totalExcitementPoints">
              <Translate contentKey="conferunsApp.talkHistory.totalExcitementPoints">Total Excitement Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.totalExcitementPoints}</dd>
          <dt>
            <span id="averageExcitementPoints">
              <Translate contentKey="conferunsApp.talkHistory.averageExcitementPoints">Average Excitement Points</Translate>
            </span>
          </dt>
          <dd>{talkHistoryEntity.averageExcitementPoints}</dd>
          <dt>
            <Translate contentKey="conferunsApp.talkHistory.talk">Talk</Translate>
          </dt>
          <dd>{talkHistoryEntity.talkId ? talkHistoryEntity.talkId : ''}</dd>
          <dt>
            <Translate contentKey="conferunsApp.talkHistory.presenter">Presenter</Translate>
          </dt>
          <dd>{talkHistoryEntity.presenterId ? talkHistoryEntity.presenterId : ''}</dd>
        </dl>
        <Button tag={Link} to="/talk-history" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/talk-history/${talkHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ talkHistory }: IRootState) => ({
  talkHistoryEntity: talkHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TalkHistoryDetail);
