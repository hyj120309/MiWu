package com.github.miwu.miot

import com.github.miwu.miot.device.DeviceType
import com.github.miwu.miot.widget.MiotBaseWidget
import miot.kotlin.model.att.SpecAtt
import miot.kotlin.utils.parseUrn

interface SpecAttHelper {

   fun DeviceType.forEachAtt(att: SpecAtt):DeviceType {
        for (service in att.services) {
            val serviceUrn = service.type.parseUrn()
            val name = serviceUrn.name
            val siid = service.iid
            service.properties?.let {
                for (property in it) {
                    val propertyUrn = property.type.parseUrn()
                    property.apply {
                        onPropertyFound(siid, name, iid, propertyUrn.name, this)
                    }
                }
            }
            service.actions?.let {
                for (action in it) {
                    val actionUrn = action.type.parseUrn()
                    action.apply {
                        onActionFound(siid, name, iid, actionUrn.name, this)
                    }
                }
            }
        }
       return this
    }

    fun onPropertyFound(
        siid: Int,
        service: String,
        piid: Int,
        property: String,
        obj: SpecAtt.Service.Property,
    )

    fun onActionFound(
        siid: Int,
        service: String,
        aiid: Int,
        action: String,
        obj: SpecAtt.Service.Action,
    )


}