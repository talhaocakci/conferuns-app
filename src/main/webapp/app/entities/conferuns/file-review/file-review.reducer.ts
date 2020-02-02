import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFileReview, defaultValue } from 'app/shared/model/conferuns/file-review.model';

export const ACTION_TYPES = {
  SEARCH_FILEREVIEWS: 'fileReview/SEARCH_FILEREVIEWS',
  FETCH_FILEREVIEW_LIST: 'fileReview/FETCH_FILEREVIEW_LIST',
  FETCH_FILEREVIEW: 'fileReview/FETCH_FILEREVIEW',
  CREATE_FILEREVIEW: 'fileReview/CREATE_FILEREVIEW',
  UPDATE_FILEREVIEW: 'fileReview/UPDATE_FILEREVIEW',
  DELETE_FILEREVIEW: 'fileReview/DELETE_FILEREVIEW',
  RESET: 'fileReview/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFileReview>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FileReviewState = Readonly<typeof initialState>;

// Reducer

export default (state: FileReviewState = initialState, action): FileReviewState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FILEREVIEWS):
    case REQUEST(ACTION_TYPES.FETCH_FILEREVIEW_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FILEREVIEW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FILEREVIEW):
    case REQUEST(ACTION_TYPES.UPDATE_FILEREVIEW):
    case REQUEST(ACTION_TYPES.DELETE_FILEREVIEW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FILEREVIEWS):
    case FAILURE(ACTION_TYPES.FETCH_FILEREVIEW_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FILEREVIEW):
    case FAILURE(ACTION_TYPES.CREATE_FILEREVIEW):
    case FAILURE(ACTION_TYPES.UPDATE_FILEREVIEW):
    case FAILURE(ACTION_TYPES.DELETE_FILEREVIEW):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FILEREVIEWS):
    case SUCCESS(ACTION_TYPES.FETCH_FILEREVIEW_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FILEREVIEW):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FILEREVIEW):
    case SUCCESS(ACTION_TYPES.UPDATE_FILEREVIEW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FILEREVIEW):
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

const apiUrl = 'api/file-reviews';
const apiSearchUrl = 'api/_search/file-reviews';

// Actions

export const getSearchEntities: ICrudSearchAction<IFileReview> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FILEREVIEWS,
  payload: axios.get<IFileReview>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IFileReview> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FILEREVIEW_LIST,
  payload: axios.get<IFileReview>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFileReview> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FILEREVIEW,
    payload: axios.get<IFileReview>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFileReview> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FILEREVIEW,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFileReview> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FILEREVIEW,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFileReview> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FILEREVIEW,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
