
<% ui.decorateWith("appui", "standardEmrPage") %>
<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>

<script type="text/javascript">
   var breadcrumbs = [


        { label: "Reply/Forward Message"}
    ];
</script>

${ ui.includeFragment("internlmsgs", "msgReplyForwardFrag") }

<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>