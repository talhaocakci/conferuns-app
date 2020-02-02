import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './talk.reducer';
import { ITalk } from 'app/shared/model/conferuns/talk.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TalkDetail extends React.Component<ITalkDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { talkEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsTalk.detail.title">Talk</Translate> [<b>{talkEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="language">
                <Translate contentKey="conferunsApp.conferunsTalk.language">Language</Translate>
              </span>
            </dt>
            <dd>{talkEntity.language}</dd>
            <dt>
              <span id="mainTopic">
                <Translate contentKey="conferunsApp.conferunsTalk.mainTopic">Main Topic</Translate>
              </span>
            </dt>
            <dd>{talkEntity.mainTopic}</dd>
            <dt>
              <span id="subTopic">
                <Translate contentKey="conferunsApp.conferunsTalk.subTopic">Sub Topic</Translate>
              </span>
            </dt>
            <dd>{talkEntity.subTopic}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="conferunsApp.conferunsTalk.status">Status</Translate>
              </span>
            </dt>
            <dd>{talkEntity.status}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsTalk.presenter">Presenter</Translate>
            </dt>
            <dd>{talkEntity.presenterId ? talkEntity.presenterId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/talk" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/talk/${talkEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ talk }: IRootState) => ({
  talkEntity: talk.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TalkDetail);
