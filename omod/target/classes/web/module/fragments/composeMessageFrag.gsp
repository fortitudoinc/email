<br><br>



<form id="createNewMessageForm" method="post">

<div class="fields" id="viewMessageRecipientsDiv">
<input id="recipientNames" size="100" placeholder="To" name="recipientNames"/><br>
</div>

<div class="fields" id="viewSubjectDiv">
<textarea rows="1" cols="50" id="subject" name="subject"  placeholder="Subject"></textarea><br>
</div>


<div class="fields" id="viewBodyDiv">
<textarea rows="10" cols="50" id="body" name="body"></textarea><br>
</div>

<div class="fields" id="viewSenderUserIdDiv">
<input type="hidden" id="senderUserId" name="senderUserId" value="$context.authenticatedUser.userId"/>
</div>

<div class="fields" id="viewRecipientUserIdsDiv">
<input type="hidden" id="recipUserIds" name="recipientUserIds" />
</div>

<div class="fields" id="sendOrDraftDiv">
<input type="hidden" id="sendOrDraft" name="sendOrDraft" value="send"/>
</div>

<button type="button" id="sendButton" onclick="sendMessage()">Send</button>
<button type="button" id="draftButton" onclick="saveDraft()">Save Draft</button>
            <a href="#" id="cancelId" >Cancel</a>
</form>


<script type="text/javascript">

var results = [];
var aRecipientUserIds;
var duplicateNames = [];
var userArray = [];


function sendMessage() {
var recipientUserIds = "";
    var recipients = jq("#recipientNames").val();
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
                     'sendOrDraft': jq("#sendOrDraft").val()

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
    jq("#sendOrDraft").val("draft");
    sendMessage();
};

jq("#cancelId").click(function() {
    window.close();
});

function recipientIsValidUser(name,users) {
    if (name.length == 0) {
        return true;
    }
    for (index in users) {
        var user = users[index];
        if (name.valueOf() === (user.givenName + " " + user.familyName).valueOf()) {
            aRecipientUserIds += user.userId + ",";
            return true;
            }
    };
    return false;
};

 </script>
 <script type="text/javascript">
  jq( function() {
    function split( val ) {
      return val.split( "," );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    jq( "#recipientNames" )
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
var query1 = jq("#userNames").val();
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

  }) ;

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