import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/schedule-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScheduleItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ScheduleItemDetail = (props: IScheduleItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { scheduleItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.scheduleItem.detail.title">ScheduleItem</Translate> [<b>{scheduleItemEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fromTime">
              <Translate contentKey="conferunsApp.scheduleItem.fromTime">From Time</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={scheduleItemEntity.fromTime} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="tillTime">
              <Translate contentKey="conferunsApp.scheduleItem.tillTime">Till Time</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={scheduleItemEntity.tillTime} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="conferunsApp.scheduleItem.talk">Talk</Translate>
          </dt>
          <dd>{scheduleItemEntity.talkId ? scheduleItemEntity.talkId : ''}</dd>
          <dt>
            <Translate contentKey="conferunsApp.scheduleItem.conference">Conference</Translate>
          </dt>
          <dd>{scheduleItemEntity.conferenceId ? scheduleItemEntity.conferenceId : ''}</dd>
        </dl>
        <Button tag={Link} to="/schedule-item" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/schedule-item/${scheduleItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ scheduleItem }: IRootState) => ({
  scheduleItemEntity: scheduleItem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ScheduleItemDetail);
