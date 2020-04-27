package com.example.w1d3_rxjavademo.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.w1d3_rxjavademo.R
import com.example.w1d3_rxjavademo.network.model.Ticket

class TicketsAdapter(appContext: Context, private var ticketList: MutableList<Ticket>,
                     private val clickListener: (Ticket) -> Unit):
    RecyclerView.Adapter<TicketViewHolder>() {

    var context = appContext

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.ticket_row, parent, false)
        return TicketViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(context, ticketList[position], clickListener)
    }

    override fun getItemCount(): Int = ticketList.size

    fun updateTickets(ticketList: List<Ticket>) {
        this.ticketList.clear()
        this.ticketList.addAll(ticketList)
        notifyDataSetChanged()
    }

    fun updatePrice(updatedTicket: Ticket, position: Int) {
        ticketList[position] = updatedTicket
        notifyItemChanged(position)
    }
}