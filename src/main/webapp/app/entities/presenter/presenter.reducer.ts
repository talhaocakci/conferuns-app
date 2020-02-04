import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPresenter, defaultValue } from 'app/shared/model/presenter.model';

export const ACTION_TYPES = {
  FETCH_PRESENTER_LIST: 'presenter/FETCH_PRESENTER_LIST',
  FETCH_PRESENTER: 'presenter/FETCH_PRESENTER',
  CREATE_PRESENTER: 'presenter/CREATE_PRESENTER',
  UPDATE_PRESENTER: 'presenter/UPDATE_PRESENTER',
  DELETE_PRESENTER: 'presenter/DELETE_PRESENTER',
  RESET: 'presenter/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPresenter>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PresenterState = Readonly<typeof initialState>;

// Reducer

export default (state: PresenterState = initialState, action): PresenterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRESENTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRESENTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRESENTER):
    case REQUEST(ACTION_TYPES.UPDATE_PRESENTER):
    case REQUEST(ACTION_TYPES.DELETE_PRESENTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRESENTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRESENTER):
    case FAILURE(ACTION_TYPES.CREATE_PRESENTER):
    case FAILURE(ACTION_TYPES.UPDATE_PRESENTER):
    case FAILURE(ACTION_TYPES.DELETE_PRESENTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESENTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRESENTER):
    case SUCCESS(ACTION_TYPES.UPDATE_PRESENTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRESENTER):
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

const apiUrl = 'api/presenters';

// Actions

export const getEntities: ICrudGetAllAction<IPresenter> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRESENTER_LIST,
  payload: axios.get<IPresenter>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPresenter> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRESENTER,
    payload: axios.get<IPresenter>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPresenter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRESENTER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPresenter> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRESENTER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPresenter> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRESENTER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
