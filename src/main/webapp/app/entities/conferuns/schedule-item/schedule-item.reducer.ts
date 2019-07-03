import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IScheduleItem, defaultValue } from 'app/shared/model/conferuns/schedule-item.model';

export const ACTION_TYPES = {
  SEARCH_SCHEDULEITEMS: 'scheduleItem/SEARCH_SCHEDULEITEMS',
  FETCH_SCHEDULEITEM_LIST: 'scheduleItem/FETCH_SCHEDULEITEM_LIST',
  FETCH_SCHEDULEITEM: 'scheduleItem/FETCH_SCHEDULEITEM',
  CREATE_SCHEDULEITEM: 'scheduleItem/CREATE_SCHEDULEITEM',
  UPDATE_SCHEDULEITEM: 'scheduleItem/UPDATE_SCHEDULEITEM',
  DELETE_SCHEDULEITEM: 'scheduleItem/DELETE_SCHEDULEITEM',
  RESET: 'scheduleItem/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IScheduleItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ScheduleItemState = Readonly<typeof initialState>;

// Reducer

export default (state: ScheduleItemState = initialState, action): ScheduleItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SCHEDULEITEMS):
    case REQUEST(ACTION_TYPES.FETCH_SCHEDULEITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SCHEDULEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SCHEDULEITEM):
    case REQUEST(ACTION_TYPES.UPDATE_SCHEDULEITEM):
    case REQUEST(ACTION_TYPES.DELETE_SCHEDULEITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SCHEDULEITEMS):
    case FAILURE(ACTION_TYPES.FETCH_SCHEDULEITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SCHEDULEITEM):
    case FAILURE(ACTION_TYPES.CREATE_SCHEDULEITEM):
    case FAILURE(ACTION_TYPES.UPDATE_SCHEDULEITEM):
    case FAILURE(ACTION_TYPES.DELETE_SCHEDULEITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SCHEDULEITEMS):
    case SUCCESS(ACTION_TYPES.FETCH_SCHEDULEITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCHEDULEITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SCHEDULEITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_SCHEDULEITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SCHEDULEITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/schedule-items';
const apiSearchUrl = 'api/_search/schedule-items';

// Actions

export const getSearchEntities: ICrudSearchAction<IScheduleItem> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SCHEDULEITEMS,
  payload: axios.get<IScheduleItem>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IScheduleItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SCHEDULEITEM_LIST,
  payload: axios.get<IScheduleItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IScheduleItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SCHEDULEITEM,
    payload: axios.get<IScheduleItem>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IScheduleItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SCHEDULEITEM,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IScheduleItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SCHEDULEITEM,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IScheduleItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SCHEDULEITEM,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
