import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FileReview from './file-review';
import FileReviewDetail from './file-review-detail';
import FileReviewUpdate from './file-review-update';
import FileReviewDeleteDialog from './file-review-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FileReviewUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FileReviewUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FileReviewDetail} />
      <ErrorBoundaryRoute path={match.url} component={FileReview} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FileReviewDeleteDialog} />
  </>
);

export default Routes;
