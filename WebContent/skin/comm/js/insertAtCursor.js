function insertAtCursor(myFieldId, myValue) {
    var myField = document.getElementById(myFieldId);
	if (document.selection) {
		myField.focus();
		sel = document.selection.createRange();
		sel.text = myValue;
		sel.select();
	}else if (myField.selectionStart || myField.selectionStart == '0') {
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		var restoreTop = myField.scrollTop;
		myField.value = myField.value.substring(0, startPos) + myValue
				+ myField.value.substring(endPos, myField.value.length);
		if (restoreTop > 0) {
			myField.scrollTop = restoreTop;
		}
		myField.focus();
		myField.selectionStart = startPos + myValue.length;
		myField.selectionEnd = startPos + myValue.length;
	} else {
		myField.value += myValue;
		myField.focus();
	}
}