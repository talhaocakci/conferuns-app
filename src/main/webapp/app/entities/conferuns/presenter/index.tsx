import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Presenter from './presenter';
import PresenterDetail from './presenter-detail';
import PresenterUpdate from './presenter-update';
import PresenterDeleteDialog from './presenter-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PresenterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PresenterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PresenterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Presenter} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PresenterDeleteDialog} />
  </>
);

export default Routes;
