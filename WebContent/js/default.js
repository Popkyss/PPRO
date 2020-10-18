/**
 * Funkce slouzi k naseptavani moznych pozadovanych hodnot (jako na idos,
 * seznam, google...)<br>
 * 
 * <p>
 * Vyzaduje:<br>
 * <ul>
 * <li> metodu v kontroleru, ktera bude vracet seznam hodnot k nasetpavani</li>
 * <li>vystup z metody v kontroleru pomoci
 * <code>return new JavaScriptResolution( List );</code></li>
 * </ul>
 * </p>
 * 
 * @param elemId ID elementu, nad kterym se vola tato funkce
 * @param actionName nazev funkce v kontroleru, ktera bude vracet hodnoty k naseptavani
 */
function naseptavat(elemId, actionName) {
	var form = document.getElementById(elemId);
	if(form == null) {
		return;
	}
	if (form.tagName != 'FORM') {
		form = $(elemId).up('form');
	}
	
	jQuery('#' + elemId).autocomplete(
			{
				source : function(request, response) {
							new Ajax.Updater('', form.action + '?' + actionName + '=&sept=' + form[elemId].value, 
									{	method : 'post',
										parameters : form.serialize( { submit : form }),
										onSuccess : function(xhr) {	response(eval(opravaJsResponse(xhr.responseText))); }
									});
						},
				minLength : 1,
				delay : 500
			});
}


/**
 * Provede opravu skriptu, ktery je vysledkem naseptavace. Stripes obsahuji chybu,
 * kterou je treba timto napravit
 */
function opravaJsResponse(responseText){
    responseText = responseText.replace(/_sj_-/g, "_sj_");
    return responseText;
}


/**
 * Prida naseptavani na definovany element
 * @param element objekt elementu, na ktery se ma pridat naseptavani
 * @param metoda nazev metody, ktera prida naseptavac
 */
function pridejNaseptavac(element, metoda) {
	if(!jQuery(element).hasClass('ui-autocomplete-input')) {
		naseptavat(element.id, metoda);
	}
}


/**
 * Odesle formular 
 * @param formId
 * @param method
 */
function submitFormSimple(formId, method) {
	var form = document.getElementById(formId);
	var action = form.action;
	form.action = form.action + "?" + method + "=";
	form.submit();
	form.action = action;
}

/**
 * Odesle formular a nastavi promennou
 * 
 * @param formId
 *            id formulare
 * @param method
 *            obsluzna metoda
 * @param nazevHidden
 *            nazev promenne, kterou nastavuje
 * @param parametr
 *            hodnota pro promennou
 */
function submitForm(formId, method, nazevHidden, parametr) {
	var form = document.getElementById(formId);
	form.elements[nazevHidden].value = parametr;
	var action = form.action;
	form.action = form.action + "?" + method + "=";
	form.submit();
	form.action = action;
}


/**
 * Odesle formular a zavola pozadovanou metodu.<br>
 * Vysledek zobrazi v novem/definovanem okne.
 * @param formId ID formulare
 * @param method nazev metody, na kterou se pozadavk odesle
 * @param frameName nazev framu, do ktereho se vysledek zobrazi
 */
function submitFormBlank(formId, method, frameName) {
	var form = document.getElementById(formId);
	var action = form.action;
	form.action = form.action + "?"+method+"=";
	if(!frameName) {
		frameName = "_blank";
	}
	var target0 = form.target;
	form.target = frameName;
	form.submit();
	form.action = action;
	form.target = target0;
}


/**
 * Vyvola varovne okno se zpravou vyuzivajici datovou prepravku warningDialogData, 
 * po potvrzeni se vyvola yesMetoda pri nepotvrzeni noMetoda a provede se submit
 * 
 * @param formId ID formulare
 * @param yesMetoda retezec obsahujici nazev metody pro potvrzeni
 * @param zprava retezec obsahujici varovnou zpravu
 * @param noMetoda retezec obsahujici nazev metody po kliknuti na storno
 * @param nazevPrepravky nazev datove prepravky v beanu
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error , MUSI byt vyplnen
 */
function warningMessage(formId, yesMetoda, zprava, noMetoda, nazevPrepravky, typ) {
	warningMessageCustom(formId, yesMetoda, zprava, noMetoda, nazevPrepravky, typ, "Ano", "Ne");
}


/**
 * Vyvola varovne okno se zpravou vyuzivajici datovou prepravku warningDialogData, 
 * po potvrzeni se vyvola yesMetoda pri nepotvrzeni noMetoda a provede se submit
 * 
 * @param formId ID formulare
 * @param yesMetoda retezec obsahujici nazev metody pro potvrzeni
 * @param zprava retezec obsahujici varovnou zpravu
 * @param noMetoda retezec obsahujici nazev metody po kliknuti na storno
 * @param nazevPrepravky nazev datove prepravky v beanu
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error , MUSI byt vyplnen
 * @param yesButton popisek tlacitko pro potvrzeni (ano)
 * @param noButton popisek tlacitka pro zruseni (ne)
 */
function warningMessageCustom(formId, yesMetoda, zprava, noMetoda, nazevPrepravky, typ, yesButton, noButton, idDiv, dialogWidth) {
	var nazevAplikace = document.getElementById("appName").value;
	// dialog pro potvrzeni
	var form = document.getElementById(formId);
	
	if(noButton == null || noButton == "null") {
		var dialogDiv = !idDiv ?  '<div></div>' : ('#' + idDiv);
		jQuery(dialogDiv).css('display', 'inline');
		$dialog = jQuery(dialogDiv);
		if(zprava != null && zprava != "") {
			$dialog = $dialog.html(zprava);
		}
		$dialog = $dialog.dialog({
			title: nazevAplikace,
			dialogClass: "no-close",
//			closeOnEscape: false,
			autoOpen: false,
			width: !dialogWidth ? 300 : dialogWidth,
			modal: true,
			open: function (event, ui) {
				$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").removeClass("ui-widget-header").addClass("msg"+typ.toUpperCase());
				setTimeout(function() {
					$('jquery-button-toklis-ano' + zprava).focus();
	            }, 50); // After 50 ms
			},
			buttons: [
			          {
			        	  text : yesButton,
			        	  id: "jquery-button-toklis-ano" + zprava,
			        	  
			        	  click: function() {
			        		  $dialog.dialog('close');
			        		  if(yesMetoda != null && yesMetoda != "null") {
			        			  // pridani metody a odeslani formulare
			        			  var formAction = form.action;
			        			  form.action = form.action + "?" + yesMetoda +"=&actionBean." + nazevPrepravky + ".zobrazit=false&actionBean." + nazevPrepravky + ".potvrzeno=true";
			        			  form.submit();
			        			  form.action = formAction;
			        		  }
			        	  }
			          }
			          ]
		});
		
		// dialogove okno vyskoci
		$dialog.dialog('open');
		// musi se vratit false, jinak dojde k automatickemu souhlasu 
		return false;
	}
	
	
	var $dialog = jQuery('<div></div>')
	.html(zprava)
	.dialog({
			title: nazevAplikace,
			autoOpen: false,
			open: function () {
				$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("msg"+typ.toUpperCase());
            },
			modal: true,
			buttons: [
			     {
			    text : yesButton,
				//tlacitko souhlasu
				click: function() {
					$dialog.dialog('close');
					// pridani metody a odeslani formulare
					var action = form.action;
					form.action = form.action + "?" + yesMetoda +"=&actionBean." + nazevPrepravky + ".zobrazit=false&actionBean." + nazevPrepravky + ".potvrzeno=true";
					form.submit();
					form.action = action;
				}
			    },
			    {
			    text : noButton,
				//tlacitko nesouhlasu
				click: function() {
					$dialog.dialog('close');
					if(noMetoda != null && noMetoda != "null") {
						// pridani metody a odeslani formulare
						var action = form.action;
						form.action = form.action + "?" + noMetoda +"=&actionBean." + nazevPrepravky + ".zobrazit=false&actionBean." + nazevPrepravky + ".potvrzeno=false";
						form.submit();
						form.action = action;
					}
				}
			    }
			]
	});
	// dialogove okno vyskoci
	$dialog.dialog('open');
	// musi se vratit false, jinak dojde k automatickemu souhlasu 
	return false;
}


/**
 * Metoda vyvola modalni dialogove okno s textem k odsouhlaseni.<br>
 * Reaguje na link i na submit.<br>
 * Obsahuje tlacitka 'Ano', 'Ne'.<br>
 * Defaultne se zbarvi jako warning dialog (trida msgW).<br>
 * Vzdy kdyz je zavolana na odkazu, oznaci radek vekterem se odkaz naleza stylem 'radekKeZruseni'.<br>
 * Pokud je ji predan parametr typ, upravi se hlavicka dialog dle msgTYP.
 * <p>
 * priklady:<br>
 * <code>
 * submit name='testicek' onclick='return confirmSmazat(this, 'potvrd to');'>klikni<br><br>
 * beanclass="${actionBean.className}" event="testicek" class="button" <br>
 * onclick="return confirmDialog(this, '${smazatPotvrzeni}');"<br>
 * </code>
 * @param objekt vzdy this
 * @param zprava text dialogoveho okna k odsouhlaseni
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error
 * @returns {Boolean} vzdy false, jinak by doslo k automatickemu souhlasu
 */
function confirmSmazat(objekt, zprava, typ, wait) {
	var nazevAplikace = document.getElementById("appName").value;
	//pokud nebyl zadan typ dialogoveho okna, nastavi se jako warning dialog
	//zde bude typ bud undefined nebo znak s informaci o typu dialogu
	if (!!!typ) {
		typ = 'w';
	}
	if (objekt.tagName == "A") {
		// LINK
		// zvyrazneni radku ke smazani
		var tr = jQuery(objekt).closest("tr");
		//zaloha a odebrani puvodnich trid radku a nastaveni chybove tridy na radek (bude cerveny)
		var tridyPuvodni = tr.attr("class");
		tr.removeClass(tridyPuvodni);
		//toggle - pokud trida je nastavena, odstrani ji, pokud neni, nastavi ji
		tr.toggleClass('radekKeZruseni');
	}
	
	// dialog pro potvrzeni
	var $dialog = jQuery('<div></div>')
	.html(zprava)
	.dialog({
		title: nazevAplikace,
		open: function () {
			$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("msg"+typ.toUpperCase());
		},
		close : function () {
			if (objekt.tagName == "A") {
				    // pro LINK vratit styl radku zpet
				    tr.addClass(tridyPuvodni);
				    tr.toggleClass('radekKeZruseni');
				}
			},
			autoOpen: false,
			modal: true,
			buttons: [ 
			{
				text : 'Ano',
					tabIndex: -1, 		//odebere focus, focus bude na druhem tlacitku
					click : function() {
						$dialog.dialog('close');
						if (objekt.tagName == "A") {
								// LINK
								// jde se na odkaz
								if (wait) {
									waitDialog('waitDialogDiv', true);
								}
								window.location = objekt;
							} else if (objekt.tagName == "INPUT") {
								// SUBMIT
								// ziskani formulare
								if (wait) {
									waitDialog('waitDialogDiv', true);
								}
								var form = $(objekt).up('form');
								var action = form.action;
								// pridani metody a odeslani formulare
								form.action = form.action + "?" + objekt.name + "=";
								form.submit();
								form.action = action;
							}
						}
					},
					{
						text : 'Ne',
						click : function() {
							$dialog.dialog('close');
						}
					}]
				});
	// dialogove okno vyskoci
	$dialog.dialog('open');
	// musi se vrati false, jinak dojde k automatickemu souhlasu 
	return false;
}


/**
 * Metoda vyvola modalni dialogove okno s textem k odsouhlaseni.<br>
 * Reaguje na link i na submit.<br>
 * Obsahuje tlacitka 'Ano', 'Ne'.<br>
 * Defaultne se zbarvi jako warning dialog (trida msgW).<br>
 * Pokud je predan atribut 'typ', zmeni se barva hlavicky a pokud je metoda zavolana
 * nad odkazem, radek ve kterem se odkaz naleza se podbarvi dle tridy msgTYP
 * <p>
 * priklady:<br>
 * <code>
 * submit name='testicek' onclick='return confirmDialog(this, 'potvrd to');'>klikni<br><br>
 * beanclass="${actionBean.className}" event="testicek" class="button" <br>
 * onclick="return confirmDialog(this, '${smazatPotvrzeni}');"<br>
 * </code>
 * @param objekt vzdy this
 * @param zprava text dialogoveho okna k odsouhlaseni
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error
 * @returns {Boolean} vzdy false, jinak by doslo k automatickemu souhlasu
 */
function confirmDialog(objekt, zprava, typ, wait) {
	var tr;
	var classPuvodni;
	var nazevAplikace = document.getElementById("appName").value;
	var zvyraznit;

	//nastaveni zda se bude zvyraznovat radek 
	//zde bude typ bud undefined nebo znak s informaci o typu dialogu
	zvyraznit = typ === undefined ? false : true;	// vraci true pouze v pripade, ze typ nebyl metode predan
	
	if (objekt.tagName == "A" && zvyraznit) {
		// LINK - zvyrazneni radku ke smazani, pokud byl zadan typ jakym se ma zvyraznovat
		tr = $(objekt).up('tr');
		classPuvodni = tr.className;
		tr.className = " msg"+typ.toUpperCase();
	}
	
	if (!zvyraznit) {
		//pokud se nezvyraznuje, nebyl pridan typ, hlavicka bude mit defaulni msgW tridu
		typ = 'w';
	}
		
	// dialog pro potvrzeni
	var $dialog = jQuery('<div></div>')
	.html(zprava)
	.dialog({
			title: nazevAplikace,
			open: function () {
				$dialog.parents('.ui-dialog:first').find('.ui-dialog-titlebar').addClass('msg' + typ.toUpperCase());
				
				/* TOHLE JE HNUSNY HACK, KTERY JSEM BYL NUCEN UDELAT ZE ZOUFALSTVI. PROSIM BUDTE SHOVIVAVI */
				if(!!jQuery("#pdf_object")) {
					jQuery("#pdf_object").hide();
				}
            },
            close: function () {
            	if(!!jQuery("#pdf_object")) {
					jQuery("#pdf_object").show();
				}
            	
            	// Aby se nezvyraznoval radek
            	if (objekt.tagName == "A") {
					// pro LINK vratit styl radku zpet
					if (zvyraznit) {
						// vrati se styl radku zpet, pokud byl pred tim zmenen
						tr.className = classPuvodni;
					}
				}
            },
            /* KONEC HNUSNEHO HACKU */
            
			autoOpen: false,
			modal: true,
			buttons: {
				//tlacitko souhlasu
				Ano: function() {
					$dialog.dialog('close');
					if (objekt.tagName == "A") {
						// LINK
						if (zvyraznit) {
							// vrati se styl radku zpet, pokud byl pred tim zmenen
							tr.className = classPuvodni;
						}
						if (wait) {
							waitDialog('waitDialogDiv', true);
						}
						
						// jde se na odkaz
						window.location = objekt;
					} else if (objekt.tagName == "INPUT") {
						// SUBMIT
						// ziskani formulare
						if (wait) {
							waitDialog('waitDialogDiv', true);
						}
						var form = $(objekt).up('form');
						// pridani metody a odeslani formulare
						var action = form.action;
						form.action = form.action + "?" + objekt.name + "=";
						form.submit();
						form.action = action;
					}
				},
				//tlacitko nesouhlasu
				Ne: function() {
					$dialog.dialog('close');
					if (objekt.tagName == "A") {
						// pro LINK vratit styl radku zpet
						if (zvyraznit) {
							// vrati se styl radku zpet, pokud byl pred tim zmenen
							tr.className = classPuvodni;
						}
					}
				}
			}
	});
	// dialogove okno vyskoci
	$dialog.dialog('open');
	// musi se vratit false, jinak dojde k automatickemu souhlasu 
	return false;
	
}


/**
 * Potvrzovaci dialog s definovanim obsluzne metody pro pripad souhlasu.
 * @param form formular
 * @param zprava 
 * @param yesMetoda
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error
 * @returns {Boolean}
 */
function confirmDialogYes(form, zprava, yesMetoda, wait, typ) {
	return confirmDialogYesNo(form, zprava, yesMetoda, null, typ, wait);
}


/**
 * Potvrzovaci dialog s definovanim obsluznych metod pro souhlas i nesouhlas.<br>
 * @param form formular
 * @param zprava
 * @param yesMetoda
 * @param noMetoda
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error
 * @returns {Boolean}
 */
function confirmDialogYesNo(form, zprava, yesMetoda, noMetoda, typ, wait) {
	var nazevAplikace = document.getElementById("appName").value;
	//pokud nebyl zadan typ dialogoveho okna, nastavi se jako warning dialog
	//zde bude typ bud undefined nebo znak s informaci o typu dialogu
	if (!!!typ) {
		typ = 'w';
	}
	if (form.tagName != 'FORM') {
		form = $(form).up('form');
	}
	// dialog pro potvrzeni
	var $dialog = jQuery('<div></div>')
	.html(zprava)
	.dialog({
			title: nazevAplikace,
			autoOpen: false,
			open: function () {
				$dialog.parents(".ui-dialog:first").find(".ui-dialog-titlebar").addClass("msg"+typ.toUpperCase());
            },
			modal: true,
			buttons: {
				//tlacitko souhlasu
				Ano: function() {
					$dialog.dialog('close');
					if (wait) {
						waitDialog('waitDialogDiv', true);
					}
					// pridani metody a odeslani formulare
					var action = form.action;
					form.action = form.action + "?" + yesMetoda + "=";
					form.submit();
					form.action = action;
				},
				//tlacitko nesouhlasu
				Ne: function() {
					$dialog.dialog('close');
					// pridani metody a odeslani formulare
					if (noMetoda != null) {
						var action = form.action;
						form.action = form.action + "?" + noMetoda + "=";
						form.submit();
						form.action = action;
					}
				}
			}
	});
	// dialogove okno vyskoci
	$dialog.dialog('open');
	// musi se vrati false, jinak dojde k automatickemu souhlasu 
	return false;
}


/**
 * Vyvolani modalniho dialogu zobrazujiciho obsah dialogFormId prvku a dve tlacitka pro odeslani formulare (s a bez atributu).<br>
 * div s obsahem musi mit style="display:none" a vyplnen title (zobrazi se v hlavicce dialogu)
 * @param objekt this, nebo v pripade pouziti metody v bodyOnLoad formular (pr: document.forms[0])
 * @param dialogFormId ID divu, ktery bude nacten a zobrazen v dialogu
 * @param yesBtn zobrazeny nazev tlacitka OK
 * @param noBtn zobrazeny nazev tlacitka Storno
 * @param yesMetoda obsluzna metoda pro tlacitko OK
 * @param noMetoda obsluzna metoda pro tlacitko Storno
 * @param typ typ dialogoveho okna, default je warning, moznosti: i/I, e/E, w/W - info, warn, error
 * @param tlacitka s tlacitkama
 * @returns {Boolean} vzdy false, aby dialog okamzite nezmizel
 */
function dialogForm(objekt, dialogFormId, yesBtn, noBtn, yesMetoda, noMetoda, typ) {
	//pokud nebyl typ zadan, defaultni je w - oranzova hlavicka
	if (!!!typ) {
		typ = 'w';
	}

	// bud byl form predan, nebo se nalezne
	var form = objekt;
	if (form.tagName != 'FORM') {
		form = $(objekt).up('form');
	}
	
	// definice tlacitek
	var btns = {};
		btns[yesBtn] = {
			click: function() {
					$dialog.dialog('close');
					var action = form.action;
					form.action = form.action + "?" + yesMetoda + "=";
					form.submit();
					form.action = action;
				},
			text: yesBtn,
			id: "button_sderfdsfredsgdfsfg_0"
		};
		
		btns[noBtn] = {
			click: function() {
					$dialog.dialog('close');
					if (noMetoda != null) {
						var action = form.action;
						form.action = form.action + '?' + noMetoda + '=';
						form.submit();
						form.action = action;
					}
				},
			text: noBtn,
			id: "button_sderfdsfredsgdfsfg_1"
		};
	
	
	//zviditelneni divu s obsahem dialogu
	jQuery('#' + dialogFormId).css('display', 'inline');

	// dialog
	var $dialog = jQuery('#' + dialogFormId).dialog({
				open : function() {
					$dialog.parents('.ui-dialog:first').find('.ui-dialog-titlebar').addClass('msg' + typ.toUpperCase());
					$dialog.keypress(function(e) {
						var unicode = e.keyCode? e.keyCode : e.charCode;
						
						// jen kdyz je stisknuty enter
						if(unicode == 13) {
							e = e || window.event;
							if (typeof e.stopPropagation != "undefined") {
								e.stopPropagation();
								e.preventDefault();
							} else {
								e.cancelBubble = true;
							}
							$dialog.parent().find('#button_sderfdsfredsgdfsfg_0').trigger('click');
						}
					});
				},
				autoOpen : false,
				modal : true,
				buttons : btns,
				//prirazeni dialogu k formu aby se propsali promenne formulare
				appendTo : form
			});
	//otevreni dialogu
	$dialog.dialog('open');
	// nastaveni focusu do input pole
	$dialog.find("input,textarea").filter(":first").blur();
	$dialog.find("input,textarea").filter(":first").focus();

	return false;
}


/**
 * Podle id elementu si z dokumentu vytahne formular a submitne ho
 * @param elementId id formulare
 */
function filtrovat(elementId) {
	var form = document.getElementById(elementId);
	form.submit();
}


/** Akce po udalosti (napr. click())
 * Provede zvyrazneni nadrizeneho radku (<TR>),
 * vyvola potvrzuji dialogove okno
 *
 * Priklad:  onclick='return confirmAction(this, "Opravdu chcete zrusit zaznam ?");'
 *
 * @param objekt objekt, na kterem nastala udalost (napr. click())
 * @param mes    text otazky do dialogoveho okna
 * @return true - uzivatel potvrdil akci v dialogovem okne, false - nepotvrdil
*/
 function confirmAction(objekt, mes) {
	var nadrizeny = objekt.parentNode; // nadrizeny objekt
	var nalezen = false;
	for ( var i = 0; nadrizeny != null && i < 5 && !nalezen; i++) {
		if (nadrizeny.nodeType == 1 && nadrizeny.tagName == "TR") {
			nalezen = true;
		} else {
			nadrizeny = nadrizeny.parentNode;
		}
	}

	if (nalezen)
		var pokracovat = false;
	var classVar = nadrizeny.className;
	if (nalezen) {
		nadrizeny.className += " radekKeZruseni";
	}
	pokracovat = confirm(mes);
	if (nalezen) {
		nadrizeny.className = classVar;
	}
	if (pokracovat) {
		return true;
	} else {
		return false;
	}
}


 /**
  * Zmeni nazev akce, ktera se pri odeslani formulare vykona
  * @param formId id formulare, ktery se odesle
  * @param akce nazev akce, ktera se provede
  */
 function zmenaCiluFormu(formId, akce) {
 	var form = document.getElementById(formId);
 	var action = form.action;
 	form.action = form.action + "?" + akce + "=";
 	form.submit();
 	form.action = action;
 }
 
 
 /**
  * Zmani stav stylu elementu zadaneho pomoci id. Pouziti v kombinace s tagem dtx:stavTag
  * @param checkBox checkBox, na jehoz stav se raguje
  * @param id element, kteremu se bude menit barva
  */
 function zmenStav(checkBox, id) {
 	var span = document.getElementById(id);
 	if(checkBox.checked) {
 		span.style.backgroundColor = '#80FF80';
 	} else {
 		span.style.backgroundColor = '#FF5050';
 	}
 }
 
 
 /**
  * Pokud akce e vyjadruje stisk klavesy enter, nastavi vybrane tlacitko jako aktivni a odesle formular na akci,
  * ktera je s timto tlacitkem spojena. pouziti teto metody vyzaduje mit ve formulari pole s id odeslatDefault s hodnotou 
  * na true a mit nastavenou akci onSubmit na formulari takto: return document.getElementById('odeslatDefault').value
  * @param cil id tlacitka, ktere odpovida akci, na kterou se ma formular odeslat
  * @param e udalost stisknuti klavesy na klavesnici
  */
 function odesliNaEnter(cil, e) {
	if(e == null) {
		e = window.event;
	}
	var unicode = e.keyCode? e.keyCode : e.charCode;
	
	// jen kdyz je stisknuty enter
	if(unicode == 13) {
		// nastavime skryte pole na false -> nedojde k odeslani formulare beznou cestou
		document.getElementById("odeslatDefault").value = false;
		
		// zjistime si cil
		var akt = document.getElementById(cil);
		akt.setActive();
		
		// vytahneme si formular a odesleme ho na akci spojenou s cilem
		var form = akt.form;
		var action = form.action;
		form.action = form.action + "?" + akt.name + "=";
	 	form.submit();
	 	form.action = action;
	}
 }
 
 
 /**
  * Pomoci ajaxu provede odeslani formulare na danou akci
  * @param control prvek, na jehoz akci se zavola tato metoda
  * @param elemId id elementu, kam se vlozi vysledek resolution controleru
  * @param actionName nazev handleru v controleru
  */
 function filtrujSeznam(control, elemId, actionName) {
 	var form = control.form;
	Form.serialize(form);
 	
 	new Ajax.Updater(elemId, form.action + "?" + actionName + "=",
 	{
 		method : 'post',
 		parameters : form.serialize({ submit : control.name })
 	}
 	);
 }


 /**
  * Pomoci ajaxu provede odeslani formulare na danou akci. Kontroluje akci, ktera vedla k vyvolani metody, 
  * pokud byl stisknut tab, nic se neprovede.
  * @param control prvek, na jehoz akci se zavola tato metoda
  * @param elemId id elementu, kam se vlozi vysledek resolution controleru
  * @param actionName nazev handleru v controleru
  * @param e objekt udalosti
  */
 function filtrujSeznam2(control, elemId, actionName, e) {
 	var unicode=e.keyCode? e.keyCode : e.charCode;
 	if(unicode == 9) {
 		return;
 	}
 	
 	var form = control.form;
 	Form.serialize(form);

 	new Ajax.Updater(elemId, form.action + "?" + actionName + "=",
 	{
 		method : 'post',
 		parameters : form.serialize({ submit : control.name })
 	}
 	);
 }


 /**
  * Pomoci ajaxu provede odeslani formulare na danou akci. Kontroluje akci, ktera vedla k vyvolani metody, pokud byl stisknut tab,
  * nic se neprovede. Lze zadat dalsi parametr requestu, ktery neni soucasti formulare.
  * @param control prvek, na jehoz akci se zavola tato metoda
  * @param elemId id elementu, kam se vlozi vysledek resolution controleru
  * @param actionName nazev handleru v controleru
  * @param e objekt udalosti
  * @param paramName nazev parametru
  * @param paramValue hodnota parametru
  */
 function filtrujSeznamParam(control, elemId, actionName, e, paramName, paramValue) {
 	var unicode=e.keyCode? e.keyCode : e.charCode;
 	if(unicode == 9) {
 		return;
 	}
 	
 	var form = control.form;
 	Form.serialize(form);

 	new Ajax.Updater(elemId, form.action + "?" + actionName + "=&" + paramName + "=" + paramValue,
 	{
 		method : 'post',
 		parameters : form.serialize({ submit : control.name})
 	}
 	);
 }


 /**
  * Nastavi focus na dalsi nebo predchozi zadavaci pole pri stisknuti leve nebo prave sipky
  * @param idPole
  */
 function dalsiPole(idPredchozi, idDalsi, e) {
 	var unicode = e.keyCode ? e.keyCode : e.charCode;
 	var dalsi = null;
 	if(unicode == 37) { // sipka doleva
 		dalsi = document.getElementById(idPredchozi);
 	} else if(unicode == 39) { // sipka doprava
 		dalsi = document.getElementById(idDalsi);
 	}
 	if(dalsi != null) {
 		dalsi.focus();
 	}
 }
 
 
 /**
  * Podle toho, zda je checkox zaskrtnuty nebo ne, vybere vsechny/zadne moznosti zadaneho selectBoxu
  * @param checkBox objekt checkBoxu
  * @param selectId id selectBoxu
  */
 function vyberData(checkBox, selectId) {
 	var select = document.getElementById(selectId);
 	
 	for(var i = 0; i < select.length; i++) {
 		select.options[i].selected = checkBox.checked;
 	}
 }


 /**
  * Zjisti, zda jsou vybrany vsechny moznosti select boxu a podle toho zaskrtne/odskrtne check box
  * @param select objekt selectBoxu
  * @param idCheckBox id checkBoxu 
  */
 function zmenCheckBox(select, idCheckBox) {
 	var checkBox = document.getElementById(idCheckBox);
 	
 	for(var i = 0; i < select.length; i++) {
 		if(!select.options[i].selected) {
 			checkBox.checked = false;
 			return;
 		}
 	}
 	checkBox.checked = true;
 }
 
 
 /**
  * Najde vsechny elementy s id ve tvaru idStart0, idStart1, atd a nastavi jim naskrtnuti dle
  * stavu objektu chckbox
  * @param checkbox zaskrtavaci policko, ze ktereho se bere stav
  * @param idStart pocatek id elementu, kterym se nastavi, zda jsou vybrany/nevybrany
  */
 function vyberVseDleId(checkbox, idStart, sendChange) {
	 jQuery("[id^='" + idStart + "']").each(function() {
		 if(!sendChange) {
			 jQuery(this).prop('checked', checkbox.checked);
		 }
		 if(sendChange) {
			 if(jQuery(this).prop("checked") != checkbox.checked) {
				 jQuery(this).trigger("click");
			 }
		 }
	});
 }
 
 
 /**
  * Obarvi radek podle toho, zda je vybran, ci nikoliv
  * @param checkbox objekt checkboxu
  * @param idRadek id radku, ktery se obarvi
  */
 function vyberRadek(checkbox, idRadek) {
	 var radek = document.getElementById(idRadek);
	 if(radek != null && checkbox != null) {
		 if(checkbox.checked) {
			 radek.className += " radekVybrano";
		 } else {
			 var index = radek.className.indexOf("radekVybrano");
			 if(index != -1) {
				 radek.className = radek.className.substring(0, index);
			 }
		 }
	 }
 }
 
 
 /**
  * Zobrazi/skryje zadany element
  * @param elementId id elementu, ktery se ma zobrazit/skryt
  * @param buttonId id tlacitka, ktere ovlada zobrazovani
  */
 function rozbalSbalElement(elementId, buttonId) {
	if(jQuery("#" + buttonId).hasClass("RozbalitBtnM")) {
		// rozbalime element
		jQuery("#" + elementId).show();
	} else {
		// sbalime element
		jQuery("#" + elementId).hide();
	}
	
	// plus zmenime na minus a naopak
	jQuery("#" + buttonId).toggleClass("RozbalitBtnM");
	jQuery("#" + buttonId).toggleClass("SbalitBtnM");
	 
 }
 
 
 /**
  * Skriupt, ktery je treba pro korektni fungovani komponenty dtx:multiselect
  * @param objekt stisknute tlacitko pro rozbaleni nabidky
  * @param dialogFormId id divu obsahujici dialog s vyctem moznosti
  * @param dialogWidth sirka dialogoveho okna
  * @returns {Boolean}
  */
 function multiSelectBoxScript(objekt, dialogFormId, dialogWidth) {
 	// bud byl form predan, nebo se nalezne
 	var form = objekt;
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
 					jQuery(".ui-dialog-titlebar").hide();
 					$dialog.parents('.ui-dialog:first').css("padding-top", "0px");
 					$dialog.parents('.ui-dialog').css("width", "auto");
 					//jQuery('.ui-widget-overlay').bind('click', function(){ jQuery('#' + dialogFormId).dialog('close'); });
 					jQuery('html').bind('click', function(e) {
                     	if(!e.target.type && e.target.type != "button" && jQuery('#' + dialogFormId).dialog('isOpen') && !jQuery(e.target).is('.ui-dialog, a') && !jQuery(e.target).closest('.ui-dialog').length) {
                          	jQuery('#' + dialogFormId).dialog('close');
                          	jQuery('#' + dialogFormId).css('display', 'none');
                         }
                     });
 					$dialog.keypress(function(e) {
 						var unicode = e.keyCode? e.keyCode : e.charCode;
 						// jen kdyz je stisknuty enter
 						if(unicode == 13) {
 							form.submit();
 						}
 					});
 				},
 				resizable: false,
 				autoOpen : false,
 				modal : false,
 				closeOnEscape: true,
 				//prirazeni dialogu k formu aby se propsali promenne formulare
 				appendTo : form,
 				width: dialogWidth,
 				position: { my: "left top", at: "left bottom", of: objekt}
 			});
 	//otevreni dialogu
 	$dialog.dialog('open');
 	$dialog.css("padding", "0px");
 	
 	return false;
 }
 
 
 /**
  * Skript, ktery je treba pro korektni fungovani komponenty dtx:multiselect s pouzitou 
  * volbou pro vyber vsech polozek
  * @param select objekt selectboxu
  */
 function vyberVse(select) {
	var id = select.options[select.selectedIndex].id;
	if(id.startsWith("idOptionMultiselectVse")) {
		select.options[0].selected = true;
		for(var i = 1; i < select.length; i++) {
			select.options[i].selected = true;
		}
	}
 }


 /**
  * Nastavi elementu se zadanym id predanou hodnotu. Pokud se element nenajde, pak nic nenastavi
  */
 function nastavHodnotu(idElementu, hodnota) {
	 var elem = document.getElementById(idElementu);
	 if(elem != null) {
		 elem.value = hodnota;
	 }
 }
 
 
 /**
  * Oppravi oznaceni tavby zadane v danem elementu
  * @param element objekt vstupniho pole
  */
 function opravTavbu(element) {
	var text = element.value;
	if (text == null || text == "") {
		return;
	} else {
		var tavba = text.substr(0, 1).toUpperCase();
		if (text.length > 1) {
			tavba += text.substr(1).toLowerCase();
		}
		
		element.value = tavba;
	}
}
 

/**
 * bude refreshovat stranku v danem intervalu, 
 * pokud se skript navaze na nejakou udalost, napr onclick tak se refresh provede pouze jednou a to az po ubehnuti intervalu
 * @param interval ms
 */
 function refreshPage(interval) {
 	setTimeout(function() {
 		location.reload();
 	}, interval);
 }
 

/**
 * Funkce se valo pri vraceni naradi ze stavu navrzeneho na odeslani/vyrazeni zpet do skladu
 * Zobrazi dialog pro potvrzeni a nastavi do skryteho elementu poradi radku, na ktere bylo kliknuto
 * @param link objekt odkazu (tlacitko, na ktere uzivatel klepne)
 * @param poradiId element pro nastaveni poradi pri odeslani
 * @param poradi hodnota poradi
 * @param duvodId id divu pro rozbrazeni v dialogovem okne
 * @param btnA tlactiko pro prvni akci
 * @param btnB tlacitko pro druhou akci
 * @param actA prvni akce
 * @param actB druha akce
 * @returns
 */
function vraceniNaradi(link, poradiId, poradi, duvodId, btnA, btnB, actA, actB) {
	document.getElementById(poradiId).value = poradi;
	return dialogForm(link, duvodId, btnA, btnB, actA, actB);
}


/**
 * Aktivuje/deaktivuje tlacitko se zadanym id na zaklade toho, jestli je v zadanem vstupnim poli
 * neco napsano
 * @param textInput vstupni textove pole
 * @param idElement id tlacitka
 */
function aktivaceTlacitka(textInput, idElement) {
	jQuery("#" + idElement).prop('disabled', textInput.value == "");
}


/**
 * Zobrazovani casu k odhlaseni overeneho uzivatele
 */
function showTimeToLogOut() {
	var cas = document.getElementById("timeToLogOutInput").value;
	if (!cas || cas == 'null') {
		return;
	}
	var casTed = document.getElementById("timeNowInput").value;
	document.getElementById("timeNowInput").value = parseInt(casTed) + 1000; 
	var zbyva = cas - casTed;
	var minuty = Math.floor(zbyva / 60000);
	var vteriny = Math.floor((zbyva-(minuty*60000)) / 1000);
	
	if (!!zbyva && zbyva >= 0) {
		document.getElementById("timeSpan").innerHTML = pad(minuty, 2) + ":" + pad(vteriny, 2) + " |";
	} else {
		if (!!document.getElementById("nameSpan")) {
			document.getElementById("nameSpan").style.display = 'none';
		}
	}
}


function pad(num, size) {
    var s = "000000000" + num;
    return s.substr(s.length-size);
}


/**
 * Po nacteni dokumentu zobrazuje odpocet pro odhlaseni overeneho uzivatele
 */
function odpocetOdhlaseni() {
	showTimeToLogOut();
	window.setInterval(showTimeToLogOut, 1000);
}

/**
 * Metoda, ktera udela bublinu nad elementem
 * @param selector element, nad kterym chceme provest bublinu
 * @param callback funkce, v ktere se nadefinuje, ktery znak se ma nahradit odradkovanim
 * @param tooltipClass class css
 */
function zobrazTitulek(selector, callback, tooltipClass) {
	var callbackDefault = function(callback) { 
		callback(jQuery(this).prop('title').replace(/\|/g, '<br />')); 
	};
	
	var classDefault = "custom-tooltip"; 
	
	if (!tooltipClass) {
		tooltipClass = classDefault;
	}
	
	if(!callback) {
		callback = callbackDefault;
	}
	
	jQuery(selector).each(function(i){
		jQuery(this).tooltip({
			content: callback,
			show: {
				delay: 100
			},
			track: true,
			// tohle volani pro open a close jsem vygooglil, aby nezustavaly viset otevrene tooltipy
			open: function(event, ui)
		    {
		        if (typeof(event.originalEvent) === 'undefined')
		        {
		            return false;
		        }

		        var tid = jQuery(ui.tooltip).attr('id');

		        // close any lingering tooltips
		        jQuery('div.ui-tooltip').not('#' + tid).remove();
		    },
		    close: function(event, ui)
		    {
		        ui.tooltip.hover(function()
		        {
		            jQuery(this).stop(true).fadeTo(400, 1); 
		        },
		        function()
		        {
		            jQuery(this).fadeOut('400', function()
		            {
		                jQuery(this).remove();
		            });
		        });
		    },
			tooltipClass: tooltipClass
		});
	});
}


/**
 * ajaxovy submit
 * @param urlString url
 * @param idForm identifikator formu
 * @param callback funkce volana po uspesnem submitu
 */
function submitAjax(urlString, idForm, callback) {
	jQuery.ajax({
		method: "POST",
		url: urlString,
		data: jQuery('#' + idForm).serialize(),
		context: document.body
		//success: callback
	}).done(function(data) {
		//alert(data);
		callback(data);
	});
}


/**
 * zobrazi dialog a ceka
 * @param dialogFormId
 * @returns {Boolean}
 */
function waitDialog(dialogFormId, modal) {
	createDiv(dialogFormId);
	jQuery(".ui-dialog-content").dialog("close");
 	// dialog
 	var $dialog = jQuery('#' + dialogFormId).dialog({
 				open : function() {							
 					jQuery(".ui-dialog-titlebar").hide();
 					$dialog.parents('.ui-dialog:first').removeClass("ui-widget-content");
 					showProgress(dialogFormId);
 					jQuery('#' + dialogFormId).attr('tabindex', -1).focus();
 				},
 				resizable: false,
 				autoOpen : false,
 				modal : modal,
 				closeOnEscape: true,
 				width: 200
 			});
 	//otevreni dialogu
 	$dialog.dialog('open');
 	document.getElementById(dialogFormId).style.padding = "0px 0px 0px 0px";
 	if (modal) {
 		$dialog.position({
 			my: "center",
 			at: "center",
 			of: window
 		});
	}
 	return false;
}

function createDiv(dialogFormId) {
	if (!document.getElementById(dialogFormId)) {
		var div = document.createElement('div');
		div.id = dialogFormId;
		div.className = 'waitDialogDiv';
		div.style.color = 'green';
		document.body.appendChild(div);
	}
}

function showProgress(dialogFormId) {
    var pb = document.getElementById(dialogFormId);
    pb.innerHTML = '<img src="./images/loading_animated.gif" id="pic" />';
    pb.innerHTML += '<div style="margin-top: 1px; font-size:11px;">';
    pb.innerHTML += '<b>Systém zpracovává požadavek...</b>';
    pb.innerHTML += '</div>';
    pb.style.display = '';
}

/**
 * Nastavi radky na strance a provede submit
 * @param pocetRadku
 */
function setRadkyNaStranceSubmit(element, pocetRadku) {
	var e = jQuery(element);//element not element.id
	// zjistime nejblizsi form
	var form = e.closest('form');
	// pridame hidden pole se zmenou radku
	// $("<input type='hidden' value='"+pocetRadku+"' />").attr("id", "pocetRadku").attr("name", "pocetRadku").appendTo("#"+form.id);
	 
	 jQuery('<input>').attr({
		    type: 'hidden',
		    id: 'pocetRadku',
		    name: 'pocetRadku',
		    value: pocetRadku
		}).appendTo(form);
	 var action = jQuery(form).attr("action");
	 jQuery(form).attr("action", action+"?poctyRadkuNaStranceAction=");
	 jQuery(form).submit();
	 return;
}

/**
 * Zavola next page pomoci submit
 * @param element
 * @param action
 * @param page
 */
function nextPageSubmit(element, action,page) {
	var e = jQuery(element);//element not element.id
	// zjistime nejblizsi form
	var form = e.closest('form');
	// pridame hidden pole se zmenou radku
	// $("<input type='hidden' value='"+pocetRadku+"' />").attr("id", "pocetRadku").attr("name", "pocetRadku").appendTo("#"+form.id);
	 
	 jQuery('<input>').attr({
		    type: 'hidden',
		    id: 'nextPage',
		    name: 'nextPage',
		    value: page
		}).appendTo(form);
	 var action = jQuery(form).attr("action");
	 jQuery(form).attr("action", action+"?nastaveniStrankyAction=");
	 jQuery(form).submit();
	 return;
}
