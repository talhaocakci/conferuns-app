import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ScheduleItem from './schedule-item';
import ScheduleItemDetail from './schedule-item-detail';
import ScheduleItemUpdate from './schedule-item-update';
import ScheduleItemDeleteDialog from './schedule-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ScheduleItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ScheduleItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ScheduleItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={ScheduleItem} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ScheduleItemDeleteDialog} />
  </>
);

export default Routes;
