import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/file-review.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileReviewProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const FileReview = (props: IFileReviewProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { fileReviewList, match } = props;
  return (
    <div>
      <h2 id="file-review-heading">
        <Translate contentKey="conferunsApp.fileReview.home.title">File Reviews</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.fileReview.home.createLabel">Create new File Review</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {fileReviewList && fileReviewList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.fileReview.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.fileReview.comment">Comment</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.fileReview.reviewer">Reviewer</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.fileReview.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.fileReview.file">File</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileReviewList.map((fileReview, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${fileReview.id}`} color="link" size="sm">
                      {fileReview.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={fileReview.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{fileReview.comment}</td>
                  <td>{fileReview.reviewer}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.FileReviewStatus.${fileReview.status}`} />
                  </td>
                  <td>{fileReview.fileId ? <Link to={`file/${fileReview.fileId}`}>{fileReview.fileId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${fileReview.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileReview.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileReview.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="conferunsApp.fileReview.home.notFound">No File Reviews found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ fileReview }: IRootState) => ({
  fileReviewList: fileReview.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FileReview);
