import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './talk-tag.reducer';
import { ITalkTag } from 'app/shared/model/talk-tag.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkTagDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkTagDetail = (props: ITalkTagDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { talkTagEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.talkTag.detail.title">TalkTag</Translate> [<b>{talkTagEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="tag">
              <Translate contentKey="conferunsApp.talkTag.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{talkTagEntity.tag}</dd>
          <dt>
            <Translate contentKey="conferunsApp.talkTag.talk">Talk</Translate>
          </dt>
          <dd>
            {talkTagEntity.talks
              ? talkTagEntity.talks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === talkTagEntity.talks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/talk-tag" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/talk-tag/${talkTagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ talkTag }: IRootState) => ({
  talkTagEntity: talkTag.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TalkTagDetail);
