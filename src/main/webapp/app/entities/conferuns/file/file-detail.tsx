import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './file.reducer';
import { IFile } from 'app/shared/model/conferuns/file.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FileDetail extends React.Component<IFileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsFile.detail.title">File</Translate> [<b>{fileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="conferunsApp.conferunsFile.name">Name</Translate>
              </span>
            </dt>
            <dd>{fileEntity.name}</dd>
            <dt>
              <span id="path">
                <Translate contentKey="conferunsApp.conferunsFile.path">Path</Translate>
              </span>
            </dt>
            <dd>{fileEntity.path}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="conferunsApp.conferunsFile.type">Type</Translate>
              </span>
            </dt>
            <dd>{fileEntity.type}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="conferunsApp.conferunsFile.status">Status</Translate>
              </span>
            </dt>
            <dd>{fileEntity.status}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsFile.talk">Talk</Translate>
            </dt>
            <dd>{fileEntity.talkId ? fileEntity.talkId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/file" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/file/${fileEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ file }: IRootState) => ({
  fileEntity: file.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileDetail);
