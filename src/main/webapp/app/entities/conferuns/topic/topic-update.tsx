import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './topic.reducer';
import { ITopic } from 'app/shared/model/conferuns/topic.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITopicUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITopicUpdateState {
  isNew: boolean;
}

export class TopicUpdate extends React.Component<ITopicUpdateProps, ITopicUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { topicEntity } = this.props;
      const entity = {
        ...topicEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/topic');
  };

  render() {
    const { topicEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsTopic.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsTopic.home.createOrEditLabel">Create or edit a Topic</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : topicEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="topic-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="categoryLabel" for="category">
                    <Translate contentKey="conferunsApp.conferunsTopic.category">Category</Translate>
                  </Label>
                  <AvField id="topic-category" type="text" name="category" />
                </AvGroup>
                <AvGroup>
                  <Label id="subCategoryLabel" for="subCategory">
                    <Translate contentKey="conferunsApp.conferunsTopic.subCategory">Sub Category</Translate>
                  </Label>
                  <AvField id="topic-subCategory" type="text" name="subCategory" />
                </AvGroup>
                <AvGroup>
                  <Label id="topicNameLabel" for="topicName">
                    <Translate contentKey="conferunsApp.conferunsTopic.topicName">Topic Name</Translate>
                  </Label>
                  <AvField id="topic-topicName" type="text" name="topicName" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/topic" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  topicEntity: storeState.topic.entity,
  loading: storeState.topic.loading,
  updating: storeState.topic.updating,
  updateSuccess: storeState.topic.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TopicUpdate);
