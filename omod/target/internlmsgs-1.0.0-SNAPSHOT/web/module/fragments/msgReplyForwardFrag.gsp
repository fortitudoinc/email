<script type="text/javascript">
var userArray = [];
    jq=jQuery;
    jq(function() { 
            jq("#cancelId").attr("href","/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/shutWindow.page");
        if ("$action" === "draft") {
            jq("#isOriginalMsgDraft").val("true");
        }
        jq("#returnURL").val("/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/shutWindow.page");
var r = "$msgRecipients";
        jq("#recipients").val("$msgRecipients");
        var replyForward = "/" + OPENMRS_CONTEXT_PATH + "/internlmsgs/msgReplyForward.page?action=";
        var act = "$action";
        var senderUserId = "$msg.senderUserId";
        var priorInfo;
        var newBody;
        var subject;

        if ("$action" === "forward") {
            newBody = `



Date: $msg.msgDate
From: $senderName
To: $oldMsgRecipientNames


$msg.msgBody`;
        }

        if ("$action" === "draft") {
                newBody = `$msg.msgBody`;
        }
        if ( ("$action" === "reply") || ("$action" === "replyAll") ) {
            priorInfo = "At $msg.msgDate, $senderName wrote:";
            newBody =`




` + priorInfo + `
` + `
` + `

$msg.msgBody`;
        };

jq("#body").val(newBody);


        if ("$action" === "draft") {
            subject = `$msg.msgSubject`;
        } else {
            if ("$action" === "forward") {
                subject = "fwd: "+ `$msg.msgSubject`;
            } else {
                subject = "re: "+ `$msg.msgSubject`;
            }
            }
jq("#subject").val(subject);

    function extractLast( term ) {
      return split( term ).pop();
    }

   function split( val ) {
      return val.split( "," );
    }

jq( "#recipients" )
      // don't navigate away from the field on tab when selecting an item


      .on( "keydown", function( event ) {
        if ( event.keyCode === jq.ui.keyCode.TAB &&
            jq( this ).autocomplete( "instance" ).menu.active ) {
          event.preventDefault();
        }
      })


      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
            var query = extractLast( request.term ) ;
            results = []; 
            jq.getJSON('${ ui.actionLink("getUserNameSuggestions") }',
                    {
                      'query': query
                    })


            .success(function(data) {
                for (index in data) {
                        var user = data[index];
                        var name = user.givenName + " " + user.familyName;
                        var userId = user.userId;
                        var userInfo = {id: userId, name: name};
                        userArray.push(userInfo);
                        var roles = user.roles.split(",");
                        var x; 
                        results.push(name);
                }
                response( results );
            })
        },



        focus: function() {
          // prevent value inserted on focus
          return false;
        },


        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( "," );
          return false;
        }
      });



    });


</script>

<script type="text/javascript">

function sendMessage() {
var recipientUserIds = "";
    var recipients = jq("#recipients").val();
    if (recipients.length <= 0) {
        emr.errorMessage("Select Recipients");
        return;
    }

        if (jq("#body").val().length > 2000) {
            emr.errorMessage("Too Many Characters in Message Body; Limit is 2000 Characters");
            return;
        }

       jq.getJSON('${ ui.actionLink("getAllUsers") }',
           {
            })
       .error(function(xhr, status, err) {
            alert('AJAX error ' + err);
        })
        .success(function(users) {
            var recipientUserIds = "";
            var recipArry = recipients.split(",");
            for (nextRecipIndx in recipArry) {
                var nextRecipientName = recipArry[nextRecipIndx];
                if (nextRecipientName.length === 0) {
                    continue;
                }
                var nextRecipientUserId = getRecipientUserId(nextRecipientName,users);

                recipientUserIds = recipientUserIds + nextRecipientUserId + ",";
                if ( nextRecipientUserId === 0) {
                    emr.errorMessage("Recipient is not a valid user: " + nextRecipientName);
                    return;
                }
            }
            if ( recipientUserIds.length === 0) {
                    emr.errorMessage("Select at least one recipient");
                    return;
            }
            jq("#recipUserIds").val(recipientUserIds);

            //emr.successMessage("Recipient User Ids: " + jq("#recipUserIds").val());



       jq.getJSON('${ ui.actionLink("saveMessage") }',
           {
                     'subject': jq("#subject").val(),
                     'body': jq("#body").val(),
                     'senderUserId': jq("#senderUserId").val(),
                     'recipientUserIds': jq("#recipUserIds").val(),
                     'sendOrDraft': jq("#sendOrDraft").val(),
                     'originalMsgId': jq("#originalMsgId").val(),
                     'isOriginalMsgDraft': jq("#isOriginalMsgDraft").val()

            })
       .error(function(xhr, status, err) {
            alert('AJAX error ' + err);
        })
        .success(function(users) {
window.close();

        });
            });

};

function saveDraft() {
var recipientUserIds = "";

    var recipients = jq("#recipients").val();
    if (recipients.length <= 0) {
        emr.errorMessage("Select Recipients");
        return;
    }

        if (jq("#body").val().length > 2000) {
            emr.errorMessage("Too Many Characters in Message Body; Limit is 2000 Characters");
            return;
        }

    jq("#sendOrDraft").val("draft");


       jq.getJSON('${ ui.actionLink("getAllUsers") }',
           {
            })
       .error(function(xhr, status, err) {
            alert('AJAX error ' + err);
        })
        .success(function(users) {
            var recipientUserIds = "";
            var recipArry = recipients.split(",");
            for (nextRecipIndx in recipArry) {
                var nextRecipientName = recipArry[nextRecipIndx];
                if (nextRecipientName.length === 0) {
                    continue;
                }
                var nextRecipientUserId = getRecipientUserId(nextRecipientName,users);

                recipientUserIds = recipientUserIds + nextRecipientUserId + ",";
                if ( nextRecipientUserId === 0) {
                    emr.errorMessage("Recipient is not a valid user: " + nextRecipientName);
                    return;
                }
            }
            if ( recipientUserIds.length === 0) {
                    emr.errorMessage("Select at least one recipient");
                    return;
            }
            jq("#recipUserIds").val(recipientUserIds);

       jq.getJSON('${ ui.actionLink("saveMessage") }',
           {
                     'subject': jq("#subject").val(),
                     'body': jq("#body").val(),
                     'senderUserId': jq("#senderUserId").val(),
                     'recipientUserIds': jq("#recipUserIds").val(),
                     'sendOrDraft': jq("#sendOrDraft").val(),
                     'originalMsgId': jq("#originalMsgId").val(),
                     'isOriginalMsgDraft': jq("#isOriginalMsgDraft").val()

            })
       .error(function(xhr, status, err) {
            alert('AJAX error ' + err);
        })
        .success(function(users) {
window.close();

        });




            });
};
 
function getRecipientUserId(name,users) {
    if (name.length === 0) {
        return 0;
    }
    for (index in users) {
        var user = users[index];
        if (name.valueOf() === (user.givenName + " " + user.familyName).valueOf()) {
            return user.userId;
            }
    };
    return 0;
};
</script>

<form id="saveMsgForm">
<br><br>
<div class="fields" id="from">
FROM: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$context.authenticatedUser.personName.fullName
<br>
</div>

<div class="fields" id="viewMessageRecipients">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To: <input id="recipients" size="100"  name="recipientNames" /><br>
</div>

<div class="fields" id="viewSubject">
Subject: <textarea rows="1" cols="50" id="subject" name="subject" ></textarea><br>
</div>


<div class="fields" id="viewBodyDiv">
<textarea rows="10" cols="80" id="body" name="body" ></textarea><br>
</div>

<div class="fields" id="senderUserIdDiv">
<input type="hidden" id="senderUserId" name="senderUserId" value="$context.authenticatedUser.userId"/>
</div>

<div class="fields" id="viewRecipientUserIds">
<input type="hidden" id="recipUserIds" name="recipientUserIds" />
</div>

<div class="fields" id="returnURLDiv">
<input type="hidden" id="returnURL" name="returnUrl" />
</div>

<div class="fields" id="sendOrDraftDiv">
<input type="hidden" id="sendOrDraft" name="sendOrDraft" value="send"/>
</div>

<div class="fields" id="originalMsgDiv">
    <input type="hidden" id="originalMsgId" name="originalMsgId" value="$msg.id"/>
</div>

<div class="fields" id="msgIdDiv">
    <input type="hidden" id="msgId" name="messageId" value="$msg.id"/>
</div>

<div class="fields" id="isOriginalMsgDraftDiv">
    <input type="hidden" id="isOriginalMsgDraft" name="isOriginalMsgDraft" value="false"/>
</div>

<button type="button" id="sendButton" onclick="sendMessage()">Send</button>
<button type="button" id="draftButton" onclick="saveDraft()">Save Draft</button>
            <a href="#" id="cancelId" >Cancel</a>

</form>

