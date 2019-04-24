<% ui.decorateWith("appui", "standardEmrPage") 
%>


<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>

<script type="text/javascript">
   var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "View All Messages"}
    ];
</script>



${ ui.includeFragment("internlmsgs", "viewAllMessagesFrag") }

<script type="text/javascript">
            //var OPENMRS_CONTEXT_PATH = 'openmrs';
            window.sessionContext = window.sessionContext || {
                locale: "en_GB"
            };
            window.translations = window.translations || {};
</script>
