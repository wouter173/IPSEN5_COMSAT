import { NodeType } from 'prosemirror-model';
import { Command, EditorState, Transaction } from 'prosemirror-state';

export function insertPlaceholder(type: NodeType, placeholderType: 'username' | 'fullname'): Command {
  return (state: EditorState, dispatch: ((tr: Transaction) => void) | undefined): boolean => {
    const { $from } = state.selection;
    const index = $from.index();
    if (!$from.parent.canReplaceWith(index, index, type)) {
      return false;
    }
    if (dispatch) {
      const node = type.create({ placeholderType });
      const tr = state.tr.replaceSelectionWith(node);
      dispatch(tr.scrollIntoView());
    }
    return true;
  };
}
