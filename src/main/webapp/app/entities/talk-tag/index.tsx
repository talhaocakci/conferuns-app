import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TalkTag from './talk-tag';
import TalkTagDetail from './talk-tag-detail';
import TalkTagUpdate from './talk-tag-update';
import TalkTagDeleteDialog from './talk-tag-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TalkTagDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TalkTagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TalkTagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TalkTagDetail} />
      <ErrorBoundaryRoute path={match.url} component={TalkTag} />
    </Switch>
  </>
);

export default Routes;
