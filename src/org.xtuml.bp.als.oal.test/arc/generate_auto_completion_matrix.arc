.function findLastSubtypes
  .param Inst_Ref super
  .assign attr_value = ""
  .select many subTypes related by super->R_OIR[R201]->R_RTO[R203]->R_SUPER[R204]->R_SUBSUP[R212]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .if((cardinality subTypes) > 0)
    .for each subtype in subTypes
      .invoke lastSubtypes = findLastSubtypes(subtype)
      .assign attr_value = lastSubtypes.value
    .end for
  .else
    .assign attr_value = super.Name + "\n"
  .end if
.end function
.function isSelfContainer
  .param Inst_Ref actionHome
  .assign attr_result = false
  .if(actionHome.Key_Lett == "ACT_DAB")
    .assign attr_result = true
  .elif(actionHome.Key_lett == "ACT_OPB")
    .assign attr_result = true
  .end if
.end function
.function gen_filtering
  .assign attr_result = "All results shall be added to a pre-filtered list.  The displayed list shall be filtered based on alpha-numeric characters.  Example:\n\nsel shows select and any class that starts with sel\nf shows for and any class that starts with f"
.end function
.function gen_locations
  .assign whitespace = "whitespace:\n\n\tAt the whitespace location all statement types and variable types shall be allowed.  In addition, other locations shall be present.  The self directive is determined later.  Examples of the possibilities:\n\n"
  .assign whitespace = whitespace + "\t::, ., <Class_KeyLetters>, <EE_KeyLetters>, <Port Name>...\n"
  .assign bridges = "bridges:\n\n\tAfter a successful parse of External Entity Key Letters all bridges after the EE key letters and :: characters shall show all bridges under such an EE.\n"
  .assign operations = "operation invocations: \n\n\tClass Based: Class based invocations require the class key letters to be successfully parsed.  If the :: characters follow, the list shall contain all class based operations for the class.\n\tInstance based operations work against either self or an Instance reference variable.  Once parsed and the '.' character is typed all instance based operations shall be listed.  The list shall contain the full signature and parameters entered when selected.\n"
  .assign functions = "function invocations:\n\n\tAfter the :: characters have been typed all functions shall exist in the auto completion list.  Note that whitespace must exist before the :: characters.\n"
  .assign componentPorts = "component ports:\n\n\tAfter the send keyword has been parsed all valid ports on the first parent component shall be added to the list.\n"
  .assign interfaceInvocation = "interface invocations:\n\n\tAfter the port has been parsed and the :: characters are typed the completion list shall contain all available operations and signals for the port.  Direction must be considered here, only those operations/signals with valid direction shall show in the list.\n"
  .assign associations = "association traversal:\n\n\tIf the -> characters are typed after a successful parse of an instance handle all associated classes key letters and the association shall show in the list.  This list shall contain entries like O_ATTR[R102], O_ID[R104].\n"
  .assign relPhrase = "relationship phrases:\n\n\tIf the . character is typed and follows an association (R<Numb>) within square brackets, the associated relationship phrases shall be shown in the list.\n"
  .assign events = "event generation:\n\n\tAll events shall be listed if the generate statement precedes the current cursor location.  The list shall contain any locally created event variables as well as all events labeled by <Key_Lett>:<Mning.'event_meaning'> (parameters).  After the 'to' string is typed creator and all classes in scope \n"
  .assign attr_result = "Locations: \n\n${whitespace}\n${operations}\n${bridges}\n${functions}\n${componentPorts}\n${interfaceInvocation}\n${associations}\n${relPhrase}\n${events}\n"
.end function
.select any bodyClass from instances of O_OBJ where (selected.Key_Lett == "ACT_ACT")
.select many actionHomes related by bodyClass->R_OIR[R201]->R_REL[R201]->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
.for each actionHome in actionHomes
  .assign result = "Generating auto-completion variants..."
  .invoke locations = gen_locations()
  .assign actionHomeType = "Action Home: ${actionHome.Name}\n"
  .select any statementClass from instances of O_OBJ where (selected.Key_Lett == "ACT_SMT")
  .select many statementTypes related by statementClass->R_OIR[R201]->R_REL[R201]->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .assign statements = "Possible statement types for OAL: \n\n"
  .for each statementType in statementTypes
    .invoke lastSubTypeStatements = findLastSubtypes(statementType)
    .assign statements = statements + lastSubTypeStatements.value
  .end for
  .select any variableClass from instances of O_OBJ where (selected.Key_Lett == "V_VAR")
  .select many variableTypes related by variableClass->R_OIR[R201]->R_REL[R201]->R_SUBSUP[R206]->R_SUB[R213]->R_RGO[R205]->R_OIR[R203]->O_OBJ[R201]
  .assign variables = "Possible variable types for OAL: \n\n"
  .for each variableType in variableTypes
    .if(variableType.Name == "Transient Var")
      .select many types from instances of S_DT
      .for each type in types
        .assign variables = variables + variableType.Name + ":" + type.Name + "\n"
      .end for
    .else
      .invoke lastSubTypeVariables = findLastSubtypes(variableType)
      .assign variables = variables + lastSubTypeVariables.value
    .end if
  .end for
  .invoke filtering = gen_filtering()
  .assign result = result + "\n\n${locations.result}\n${actionHomeType}\n${statements}\n${variables}\n${filtering.result}"
  .invoke selfContainer = isSelfContainer(actionHome)
  .if(not selfContainer.result)
    .assign result = result + "\n\nself is invalid in this action home"
  .else
    .if(actionHome.Key_Lett == "ACT_OPB")
      .assign result = result + "\n\nself is valid in this action home, unless the operation is class based."
    .else
      .assign result = result + "\n\nself is valid in this action home."
    .end if
  .end if
${result}
  .emit to file "$_{actionHome.Name}.txt"  
.end for