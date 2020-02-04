import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/file-review.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileReviewDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileReviewDetail = (props: IFileReviewDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { fileReviewEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="conferunsApp.fileReview.detail.title">FileReview</Translate> [<b>{fileReviewEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="conferunsApp.fileReview.date">Date</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={fileReviewEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="comment">
              <Translate contentKey="conferunsApp.fileReview.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{fileReviewEntity.comment}</dd>
          <dt>
            <span id="reviewer">
              <Translate contentKey="conferunsApp.fileReview.reviewer">Reviewer</Translate>
            </span>
          </dt>
          <dd>{fileReviewEntity.reviewer}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="conferunsApp.fileReview.status">Status</Translate>
            </span>
          </dt>
          <dd>{fileReviewEntity.status}</dd>
          <dt>
            <Translate contentKey="conferunsApp.fileReview.file">File</Translate>
          </dt>
          <dd>{fileReviewEntity.fileId ? fileReviewEntity.fileId : ''}</dd>
        </dl>
        <Button tag={Link} to="/file-review" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/file-review/${fileReviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ fileReview }: IRootState) => ({
  fileReviewEntity: fileReview.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileReviewDetail);
