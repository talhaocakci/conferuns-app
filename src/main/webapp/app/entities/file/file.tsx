import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const File = (props: IFileProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { fileList, match } = props;
  return (
    <div>
      <h2 id="file-heading">
        <Translate contentKey="conferunsApp.file.home.title">Files</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="conferunsApp.file.home.createLabel">Create new File</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {fileList && fileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.file.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.file.path">Path</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.file.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.file.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.file.talk">Talk</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileList.map((file, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${file.id}`} color="link" size="sm">
                      {file.id}
                    </Button>
                  </td>
                  <td>{file.name}</td>
                  <td>{file.path}</td>
                  <td>{file.type}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.FileStatus.${file.status}`} />
                  </td>
                  <td>{file.talkId ? <Link to={`talk/${file.talkId}`}>{file.talkId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${file.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${file.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${file.id}/delete`} color="danger" size="sm">
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
            <Translate contentKey="conferunsApp.file.home.notFound">No Files found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ file }: IRootState) => ({
  fileList: file.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(File);
