import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/conferuns/file-review.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileReviewDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FileReviewDetail extends React.Component<IFileReviewDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileReviewEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsFileReview.detail.title">FileReview</Translate> [<b>{fileReviewEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">
                <Translate contentKey="conferunsApp.conferunsFileReview.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={fileReviewEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="comment">
                <Translate contentKey="conferunsApp.conferunsFileReview.comment">Comment</Translate>
              </span>
            </dt>
            <dd>{fileReviewEntity.comment}</dd>
            <dt>
              <span id="reviewer">
                <Translate contentKey="conferunsApp.conferunsFileReview.reviewer">Reviewer</Translate>
              </span>
            </dt>
            <dd>{fileReviewEntity.reviewer}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="conferunsApp.conferunsFileReview.status">Status</Translate>
              </span>
            </dt>
            <dd>{fileReviewEntity.status}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsFileReview.file">File</Translate>
            </dt>
            <dd>{fileReviewEntity.fileId ? fileReviewEntity.fileId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/file-review" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/file-review/${fileReviewEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ fileReview }: IRootState) => ({
  fileReviewEntity: fileReview.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileReviewDetail);
