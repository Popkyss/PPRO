/**
 * 
 * @param objekt
 * @param dialogFormId
 * @param yesBtn
 * @param noBtn
 * @param yesMetoda
 * @param noMetoda
 * @returns {Boolean}
 */
function mainDialog(formId, dialogFormId, dialogWidth) {
	// bud byl form predan, nebo se nalezne
	var form = document.getElementById(formId);
	if (form.tagName != 'FORM') {
		form = $(objekt).up('form');
	}
	if (jQuery('#' + dialogFormId).css('display') == 'inline') {
		jQuery('#' + dialogFormId).dialog('close');
		jQuery('#' + dialogFormId).css('display', 'none');
		return;
	}
	
	
	//zviditelneni divu s obsahem dialogu
	jQuery('#' + dialogFormId).css('display', 'inline');

	// dialog
	var $dialog = jQuery('#' + dialogFormId).dialog({
		open : function() {							
			//jQuery(".ui-dialog-titlebar").hide();
			$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").removeClass("ui-widget-header").addClass("msgI");
			
			jQuery('.ui-widget-overlay').bind('click', function(){ jQuery('#' + dialogFormId).dialog('close'); });
		},
		close : function() {
			jQuery('#' + dialogFormId).css('display', 'none');
			//document.getElementById('zobrazitDialogHidden').value = false;
		},
		resizable: false,
		autoOpen : false,
		modal : true,
		closeOnEscape: true,
		//prirazeni dialogu k formu aby se propsali promenne formulare
		appendTo : form,
		width:'auto'
	});
	
	//prirazeni dialogu k formu aby se propsali promenne formulare
	$dialog.parent().appendTo(form);
	//otevreni dialogu
	$dialog.dialog('open');
	document.getElementById(dialogFormId).style.padding = "0px 0px 0px 0px";
	return false;
}

function mainDialog(formId, dialogFormId, dialogWidth, closeFunction) {
	// bud byl form predan, nebo se nalezne
	var form = document.getElementById(formId);
	if (form.tagName != 'FORM') {
		form = $(objekt).up('form');
	}
	if (jQuery('#' + dialogFormId).css('display') == 'inline') {
		jQuery('#' + dialogFormId).dialog('close');
		jQuery('#' + dialogFormId).css('display', 'none');
		return;
	}
	
	
	//zviditelneni divu s obsahem dialogu
	jQuery('#' + dialogFormId).css('display', 'inline');

	// dialog
	var $dialog = jQuery('#' + dialogFormId).dialog({
		open : function() {							
			//jQuery(".ui-dialog-titlebar").hide();
			$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").removeClass("ui-widget-header").addClass("msgI");
			
			jQuery('.ui-widget-overlay').bind('click', function(){ jQuery('#' + dialogFormId).dialog('close'); });
		},
		close : function() {
			jQuery('#' + dialogFormId).css('display', 'none');
			submitFormSimple(formId, closeFunction);
		},
		resizable: false,
		autoOpen : false,
		modal : true,
		closeOnEscape: true,
		//prirazeni dialogu k formu aby se propsali promenne formulare
		appendTo : form,
		width:'auto'
	});
	
	//prirazeni dialogu k formu aby se propsali promenne formulare
	$dialog.parent().appendTo(form);
	//otevreni dialogu
	$dialog.dialog('open');
	document.getElementById(dialogFormId).style.padding = "0px 0px 0px 0px";
	return false;
}

function resizeMainDialog(){
	jQuery(window).resize(function(){
		jQuery("#idDiv").dialog("option","position","center");
	});

}


function disableInputs(selectElement) {
	var value = selectElement.value;
	var txtHlaskaElement = document.getElementById("txtHlaska");
	if (!value) {
		txtHlaskaElement.value = "";
		txtHlaskaElement.disabled = true;
		return;
	} else {
		txtHlaskaElement.disabled = false;
	}
	
	var hlaska = txtHlaskaElement.value;
	var hlasky = document.getElementsByName("vlastniciHlasky");
	
	if (!hlaska) {
		txtHlaskaElement.value = document.getElementById("vlastnik" + value).value;
	} else {
		for (var i = 0; i < hlasky.length; i++) {
		var e = hlasky[i];
		
			if(hlaska == e.value) {
				txtHlaskaElement.value = document.getElementById("vlastnik" + value).value;
			}
		}
	}
		
}
